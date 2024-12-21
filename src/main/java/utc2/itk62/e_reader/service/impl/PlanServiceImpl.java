package utc2.itk62.e_reader.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.domain.entity.Plan;
import utc2.itk62.e_reader.domain.entity.Price;
import utc2.itk62.e_reader.domain.model.PlanInfo;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.PlanRepository;
import utc2.itk62.e_reader.repository.PriceRepository;
import utc2.itk62.e_reader.service.PlanService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final PriceRepository priceRepository;

    @Override
    public List<PlanInfo> getAllPlans() {
        List<Plan> plans = planRepository.findAll();
        List<Long> planIds = plans.stream().map(Plan::getId).toList();
        List<Price> prices = priceRepository.findAllLatestByPlanIdIn(planIds);
        Map<Long, Price> priceMap = prices.stream()
                .collect(Collectors.toMap(
                        Price::getPlanId,
                        price -> price
                ));
        return plans.stream().map(p -> {
            PlanInfo planInfo = new PlanInfo();
            planInfo.setId(p.getId());
            planInfo.setName(p.getName());
            planInfo.setPrice(priceMap.get(p.getId()));
            return planInfo;
        }).toList();
    }

    @Override
    public Long createPlan(String name) {
        planRepository.findByName(name).ifPresent(p -> {
            throw new EReaderException(MessageCode.PLAN_NAME_EXISTS);
        });
        Plan plan = new Plan();
        plan.setName(name);
        planRepository.save(plan);
        return plan.getId();
    }

    @Override
    public Plan updatePlan(Long id, String name) {
        Plan plan = planRepository.findById(id).orElseThrow(() -> new EReaderException("not found Plan id"));
        plan.setName(name);
        return plan;
    }

    @Override
    public void deletePlan(Long id) {
        Optional<Plan> plan = planRepository.findById(id);
        plan.ifPresent(planRepository::delete);
    }
}
