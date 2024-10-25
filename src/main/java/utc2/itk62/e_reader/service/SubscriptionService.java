package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.domain.entity.Subscription;

import java.util.Optional;

public interface SubscriptionService {
    Subscription createSubscription(Long userId, Long planId);
    boolean deleteSubscription(Long id);
    Subscription getById(Long id);
    Optional<Double> getPricePlan(Long id);
}
