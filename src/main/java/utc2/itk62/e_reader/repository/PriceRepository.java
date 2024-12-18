package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utc2.itk62.e_reader.domain.entity.Price;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query(
            value = """
                    SELECT * FROM prices p
                    WHERE id IN (
                        SELECT DISTINCT ON (plan_id) id
                        FROM prices
                        WHERE plan_id IN :planIds
                        ORDER BY plan_id, effective_date
                    )
                    """,
            nativeQuery = true
    )
    List<Price> findAllLatestByPlanIdIn(List<Long> planIds);

    @Query(
            value = """
                    SELECT * FROM prices
                    WHERE plan_id = :planId
                    ORDER BY plan_id, effective_date
                    LIMIT 1
                    """,
            nativeQuery = true
    )
    Optional<Price> findLatestByPlanId(Long planId);
}
