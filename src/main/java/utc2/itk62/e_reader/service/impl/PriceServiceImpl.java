package utc2.itk62.e_reader.service.impl;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.BookCollection;
import utc2.itk62.e_reader.domain.entity.Collection;
import utc2.itk62.e_reader.domain.entity.Plan;
import utc2.itk62.e_reader.domain.entity.Price;
import utc2.itk62.e_reader.domain.model.PriceFilter;
import utc2.itk62.e_reader.dto.price.CreatePriceRequest;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.PlanRepository;
import utc2.itk62.e_reader.repository.PriceRepository;
import utc2.itk62.e_reader.service.PriceService;

import java.util.List;

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

    @Override
    public List<Price> getListPrice(PriceFilter filter, Pagination pagination) {
        Specification<Price> spec = Specification.where(null);

        if (filter != null) {
            if (filter.getPlanId() != null) {
                spec = spec.and(((root, query, criteriaBuilder) -> criteriaBuilder.
                        equal(root.get("planId"), filter.getPlanId())));
            }
        }

        Pageable pageable = PageRequest.of(pagination.getPage() - 1, pagination.getPageSize());
        Page<Price> pagePrice = priceRepository.findAll(spec, pageable);
        pagination.setTotal(pagePrice.getTotalElements());
        return pagePrice.toList();
    }
}
