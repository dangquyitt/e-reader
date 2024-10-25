package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.domain.entity.PlanVersion;
import utc2.itk62.e_reader.domain.model.CreatePlanVersionParam;
import utc2.itk62.e_reader.domain.model.UpdatePlanVersionParam;
import utc2.itk62.e_reader.exception.CustomException;
import utc2.itk62.e_reader.repository.PlanRepository;
import utc2.itk62.e_reader.repository.PlanVersionRepository;
import utc2.itk62.e_reader.service.PlanVersionService;

import java.util.List;

@Service
@AllArgsConstructor
public class PlanVersionServiceImpl implements PlanVersionService {
    private final PlanVersionRepository planVersionRepository;
    private final PlanRepository planRepository;
    @Override
    public PlanVersion createPlanVersion(CreatePlanVersionParam createPlanVersionParam) {
        var plan = planRepository.findById(createPlanVersionParam.getPlanId()).orElseThrow(() ->
                new CustomException().addError(new Error("id", "plan.id.not_found")));
        PlanVersion planVersion = PlanVersion.builder()
                .effectiveTime(createPlanVersionParam.getEffectiveTime())
                .maxEnrollBook(createPlanVersionParam.getMaxEnrollBook())
                .price(createPlanVersionParam.getPrice())
                .plan(plan)
                .build();

        return planVersionRepository.save(planVersion);
    }

    @Override
    public PlanVersion updatePlanVersion(UpdatePlanVersionParam updatePlanVersionParam) {
        var planVersion = planVersionRepository.findById(updatePlanVersionParam.getPlanVersionId()).orElseThrow(() ->
                new CustomException().addError(new Error("id", "planVersion.id.not_found")));
        var plan = planRepository.findById(updatePlanVersionParam.getPlanId()).orElseThrow(() ->
                new CustomException().addError(new Error("id", "plan.id.not_found")));
        planVersion.setPrice(updatePlanVersionParam.getPrice());
        planVersion.setPrice(updatePlanVersionParam.getPrice());
        planVersion.setPlan(plan);
        planVersion.setEffectiveTime(updatePlanVersionParam.getEffectiveTime());

        return planVersionRepository.save(planVersion);
    }

    @Override
    public boolean deletePlanVersion(Long id) {
        var planVersion = planVersionRepository.findById(id).orElseThrow(() ->
                new CustomException().addError(new Error("id", "planVersion.id.not_found")));
        planVersionRepository.delete(planVersion);
        return !planVersionRepository.existsById(id);
    }

    @Override
    public PlanVersion getById(Long id) {
        return planVersionRepository.findById(id).orElseThrow(() ->
                new CustomException().addError(new Error("id", "planVersion.id.not_found")));
    }

    @Override
    public List<PlanVersion> getAllPlanVersion() {
        return planVersionRepository.findAll();
    }
}
