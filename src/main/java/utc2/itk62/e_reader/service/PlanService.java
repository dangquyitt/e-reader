package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.domain.entity.Plan;
import utc2.itk62.e_reader.domain.model.PlanInfo;

import java.util.List;

public interface PlanService {
    List<PlanInfo> getAllPlans();

    Long createPlan(String name);

    Plan updatePlan(Long id, String name);

    void deletePlan(Long id);
}
