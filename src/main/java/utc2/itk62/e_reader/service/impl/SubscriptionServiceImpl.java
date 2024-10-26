package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.domain.entity.Plan;
import utc2.itk62.e_reader.domain.entity.Subscription;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.exception.CustomException;
import utc2.itk62.e_reader.repository.PlanRepository;
import utc2.itk62.e_reader.repository.SubscriptionRepository;
import utc2.itk62.e_reader.repository.UserRepository;
import utc2.itk62.e_reader.service.SubscriptionService;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final SubscriptionRepository subscriptionRepository;
    @Override
    public Subscription createSubscription(Long userId, Long planId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new CustomException().addError(new Error("id","user.id.not_found")));
        Plan plan = planRepository.findById(planId).orElseThrow(() ->
                new CustomException().addError(new Error("id","plan.id.not_found")));
        Instant startTime = Instant.now();
        Instant endTime = startTime.plus(Duration.ofDays(30));
        Subscription subscription = Subscription.builder()
                .plan(plan)
                .user(user)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean deleteSubscription(Long id) {
        var subscription = subscriptionRepository.findById(id).orElseThrow(() ->
                new CustomException().addError(new Error("id","subscription.id.not_found")));
        subscriptionRepository.delete(subscription);
        return !subscriptionRepository.existsById(id);
    }

    @Override
    public Subscription getById(Long id) {
        return subscriptionRepository.findById(id).orElseThrow(() ->
                new CustomException().addError(new Error("id","subscription.id.not_found")));
    }

    @Override
    public Optional<Double> getPricePlan(Long id) {
        return subscriptionRepository.findLatestPlanVersionPriceBySubscriptionId(id);
    }

    @Override
    public List<Subscription> getAll(User user) {
        List<Subscription> subscriptions = subscriptionRepository.findAllByUser(user);
        return subscriptions;
    }
}
