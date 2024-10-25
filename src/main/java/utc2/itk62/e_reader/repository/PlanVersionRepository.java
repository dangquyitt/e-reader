package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import utc2.itk62.e_reader.domain.entity.PlanVersion;

import java.time.LocalDate;
import java.util.Optional;

public interface PlanVersionRepository extends JpaRepository<PlanVersion,Long> {

    @Query("SELECT pv FROM PlanVersion pv " +
            "WHERE pv.plan.id = :planId " +
            "AND pv.effectiveTime <= :subscriptionDate " +
            "ORDER BY pv.effectiveTime DESC")
    Optional<PlanVersion> findLatestVersionByPlanIdAndSubscriptionDate(@Param("planId") Long planId
            , @Param("subscriptionDate") LocalDate subscriptionDate);


}
