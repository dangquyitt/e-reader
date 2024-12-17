package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.domain.entity.Plan;
import utc2.itk62.e_reader.domain.entity.Price;
import utc2.itk62.e_reader.dto.price.CreatePriceRequest;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.PlanRepository;
import utc2.itk62.e_reader.repository.PriceRepository;
import utc2.itk62.e_reader.service.PriceService;

@Service
@AllArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final PlanRepository planRepository;

    @Override
    public Long createPrice(CreatePriceRequest createPriceRequest) {
        // TODO: create message
        Plan plan = planRepository.findById(createPriceRequest.getPlanId()).orElseThrow(() -> new EReaderException(""));
        Price price = new Price();
        price.setAmount(createPriceRequest.getAmount());
        price.setCurrency(createPriceRequest.getCurrency());
        price.setDuration(createPriceRequest.getDuration());
        price.setDurationUnit(createPriceRequest.getDurationUnit());
        price.setPlanId(plan.getId());
        price.setFeatures(createPriceRequest.getFeatures());
        price.setMetadata(createPriceRequest.getMetadata());
        priceRepository.save(price);
        return price.getId();
    }
}
