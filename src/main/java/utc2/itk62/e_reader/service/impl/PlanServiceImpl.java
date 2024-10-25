package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.domain.entity.Plan;
import utc2.itk62.e_reader.exception.CustomException;
import utc2.itk62.e_reader.repository.PlanRepository;
import utc2.itk62.e_reader.service.PlanService;

import java.util.List;

@Service
@AllArgsConstructor
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    @Override
    public Plan createPlan(String name) {
        Plan plan = Plan.builder()
                .name(name)
                .build();
        return planRepository.save(plan);
    }

    @Override
    public boolean deletePlan(Long id) {
        var plan = planRepository.findById(id).orElseThrow(() ->
                new CustomException().addError(new Error("id","plan.id.not_found")));
        planRepository.delete(plan);
        return !planRepository.existsById(id);
    }

    @Override
    public Plan updatePlan(String name,Long id) {
        Plan plan = planRepository.findById(id).orElseThrow(() ->
                new CustomException().addError(new Error("id","plan.id.not_found")));
        plan.setName(name);
        return planRepository.save(plan);
    }

    @Override
    public Plan getPlanById(Long id) {
        return planRepository.findById(id).orElseThrow(() ->
                new CustomException().addError(new Error("id","plan.id.not_found")));
    }

    @Override
    public List<Plan> getAllPlan() {
        return planRepository.findAll();
    }
}
