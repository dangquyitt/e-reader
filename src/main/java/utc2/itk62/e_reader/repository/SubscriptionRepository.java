package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import utc2.itk62.e_reader.domain.entity.Subscription;
import utc2.itk62.e_reader.domain.entity.User;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    Optional<Subscription> findByUserId(Long userId);
    List<Subscription> findAllByUser(User user);
    @Query("SELECT pv.price FROM PlanVersion pv " +
            "JOIN pv.plan p " +
            "JOIN Subscription s ON s.plan.id = p.id " +
            "WHERE s.id = :subscriptionId " +
            "ORDER BY pv.effectiveTime DESC")
    Optional<Double> findLatestPlanVersionPriceBySubscriptionId(@Param("subscriptionId") Long subscriptionId);

    @Query("SELECT s FROM Subscription s WHERE s.user.id = :userId AND :currentTime < s.endTime")
    List<Subscription> findActiveSubscriptionsByUserId(@Param("userId") Long userId, @Param("currentTime") Instant currentTime);
}
