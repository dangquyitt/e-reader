package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.domain.entity.PlanVersion;
import utc2.itk62.e_reader.domain.model.CreatePlanVersionParam;
import utc2.itk62.e_reader.domain.model.UpdatePlanVersionParam;

import java.util.List;

public interface PlanVersionService {
    PlanVersion createPlanVersion(CreatePlanVersionParam createPlanVersionParam);
    PlanVersion updatePlanVersion(UpdatePlanVersionParam updatePlanVersionParam);
    boolean deletePlanVersion(Long id);
    PlanVersion getById(Long id);
    List<PlanVersion> getAllPlanVersion();
}
