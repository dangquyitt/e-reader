package utc2.itk62.e_reader.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.domain.entity.Plan;
import utc2.itk62.e_reader.domain.model.PlanInfo;
import utc2.itk62.e_reader.repository.PlanRepository;
import utc2.itk62.e_reader.repository.PriceRepository;
import utc2.itk62.e_reader.service.PlanService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final PriceRepository priceRepository;

    @Override
    public List<PlanInfo> getAllPlans() {
        List<Plan> plans = planRepository.findAll();
        return null;
    }
}
