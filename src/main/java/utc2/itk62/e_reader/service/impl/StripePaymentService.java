package utc2.itk62.e_reader.service.impl;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.domain.entity.Plan;
import utc2.itk62.e_reader.domain.entity.Price;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.PlanRepository;
import utc2.itk62.e_reader.repository.PriceRepository;
import utc2.itk62.e_reader.repository.UserRepository;
import utc2.itk62.e_reader.service.PaymentService;

import java.math.BigDecimal;

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

    public String generatePaymentURL(Long userId, Long planId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EReaderException(MessageCode.USER_ID_NOT_FOUND));
        Plan plan = planRepository.findById(planId).orElseThrow(() -> new EReaderException(MessageCode.PLAN_ID_NOT_FOUND));
        Price price = priceRepository.findLatestByPlanId(planId).orElseThrow(() -> new EReaderException(MessageCode.PRICE_LATEST_NOT_FOUND));
        int intPart = price.getAmount().intValue();
        int decimalPart = price.getAmount().subtract(new BigDecimal(intPart)).multiply(new BigDecimal(100)).intValue();
        long amount = (long) intPart * 100 + decimalPart;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(CLIENT_URL + "/payments/success")
                .setCancelUrl(CLIENT_URL + "/payments/cancel")
                .setCustomerEmail(user.getEmail())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(price.getCurrency())
                                                .setUnitAmount(amount)
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
}
