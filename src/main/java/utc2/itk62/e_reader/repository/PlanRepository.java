package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utc2.itk62.e_reader.domain.entity.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
}