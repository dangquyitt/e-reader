package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.entity.Subscription;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>, JpaSpecificationExecutor<Subscription> {
    @Query(
            value = """
                        SELECT * FROM subscriptions
                        WHERE user_id = :userId
                        ORDER BY end_time DESC
                        LIMIT 1
                    """,
            nativeQuery = true
    )
    Optional<Subscription> findByLatestEndTimeAndUserId(Long userId);

    Optional<Subscription> findByIdAndUserId(Long id, Long userId);
}
