package utc2.itk62.e_reader.service.impl;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import utc2.itk62.e_reader.constant.DurationUnit;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.domain.entity.Plan;
import utc2.itk62.e_reader.domain.entity.Price;
import utc2.itk62.e_reader.domain.entity.Subscription;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.PlanRepository;
import utc2.itk62.e_reader.repository.PriceRepository;
import utc2.itk62.e_reader.repository.SubscriptionRepository;
import utc2.itk62.e_reader.repository.UserRepository;
import utc2.itk62.e_reader.service.MailService;
import utc2.itk62.e_reader.service.PaymentService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class StripePaymentService implements PaymentService {
    @Value("${application.client.url}")
    private String CLIENT_URL;
    private final StripeClient stripeClient;
    private final PlanRepository planRepository;
    private final PriceRepository priceRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    private long convertToStripeAmount(BigDecimal localAmount) {
        int intPart = localAmount.intValue();
        int decimalPart = localAmount.subtract(new BigDecimal(intPart)).multiply(new BigDecimal(100)).intValue();
        return (long) intPart * 100 + decimalPart;
    }

    public String generatePaymentURL(Long userId, Long planId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EReaderException(MessageCode.USER_ID_NOT_FOUND));
        Plan plan = planRepository.findById(planId).orElseThrow(() -> new EReaderException(MessageCode.PLAN_ID_NOT_FOUND));
        Price price = priceRepository.findLatestByPlanId(planId).orElseThrow(() -> new EReaderException(MessageCode.PRICE_LATEST_NOT_FOUND));

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(CLIENT_URL + "/payments/success")
                .setCancelUrl(CLIENT_URL + "/payments/cancel")
                .setCustomerEmail(user.getEmail())
                .putMetadata("userId", String.valueOf(userId))
                .putMetadata("planId", String.valueOf(planId))
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(price.getCurrency())
                                                .setUnitAmount(convertToStripeAmount(price.getAmount()))
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(plan.getName()).build()
                                                ).build()
                                ).build()
                )
                .build();
        try {
            Session session = stripeClient.checkout().sessions().create(params);
            return session.getUrl();
        } catch (StripeException e) {
            log.error("StripeException | {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Exception | {}", e.getMessage());
            throw new EReaderException(MessageCode.PAYMENT_ERROR);
        }
    }

    @Override
    @Transactional
    public void confirmPayment(Session session) {
        try {
            Map<String, String> metadata = session.getMetadata();
            Long userId = Long.parseLong(metadata.get("userId"));
            Long planId = Long.parseLong(metadata.get("planId"));
            Optional<Price> price = priceRepository.findLatestByPlanId(planId);
            if (price.isEmpty()) {
                log.error("Price not found | {}", planId);
                return;
            }

            Subscription newSubscription = new Subscription();
            newSubscription.setPriceId(price.get().getId());
            newSubscription.setUserId(userId);
            Optional<Subscription> subscription = subscriptionRepository.findByLatestEndTimeAndUserId(userId);
            if (subscription.isPresent()) {
                newSubscription.setStartTime(subscription.get().getEndTime());
            } else {
                newSubscription.setStartTime(Instant.now());
            }

            newSubscription.setEndTime(newSubscription.getStartTime().plusSeconds(getSecondsByDuration(price.get().getDurationUnit(), price.get().getDuration())));
            long stripeAmount = convertToStripeAmount(price.get().getAmount());
            if (stripeAmount != session.getAmountTotal() || !price.get().getCurrency().equalsIgnoreCase(session.getCurrency())) {
                log.error("difference amount or currency | app-amount: {}. stripe-amount: {}. app-currency: {}. stripe-currency: {}", stripeAmount, session.getAmountTotal(), price.get().getCurrency(), session.getCurrency());
                return;
            }
            subscriptionRepository.save(newSubscription);
        } catch (Exception e) {
            log.error("Exception | {}", e.getMessage());
        }
    }

    private long getSecondsByDuration(String durationUnit, int duration) {
        long secondsInDay = 24 * 3600;
        return switch (durationUnit) {
            case DurationUnit.DAY -> (long) duration * secondsInDay;
            case DurationUnit.MONTH -> duration * 30 * secondsInDay;
            case DurationUnit.YEAR -> duration * 365 * secondsInDay;
            default -> throw new EReaderException("Cannot convert duration");
        };
    }
}
