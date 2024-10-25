package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.domain.entity.Plan;

import java.util.List;

public interface PlanService {
    Plan createPlan(String name);
    boolean deletePlan(Long id);
    Plan updatePlan(String name,Long id);
    Plan getPlanById(Long id);
    List<Plan> getAllPlan();
}
