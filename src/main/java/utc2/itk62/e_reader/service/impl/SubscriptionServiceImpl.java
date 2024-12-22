package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Plan;
import utc2.itk62.e_reader.domain.entity.Price;
import utc2.itk62.e_reader.domain.entity.Subscription;
import utc2.itk62.e_reader.domain.model.CreateSubscriptionParam;
import utc2.itk62.e_reader.domain.model.OrderBy;
import utc2.itk62.e_reader.domain.model.SubscriptionFilter;
import utc2.itk62.e_reader.dto.price.PriceDetail;
import utc2.itk62.e_reader.dto.subscription.SubscriptionDetail;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.PlanRepository;
import utc2.itk62.e_reader.repository.PriceRepository;
import utc2.itk62.e_reader.repository.SubscriptionRepository;
import utc2.itk62.e_reader.service.SubscriptionService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final PriceRepository priceRepository;
    private final PlanRepository planRepository;


    @Override
    public Subscription addSubscription(CreateSubscriptionParam createSubscriptionParam) {
        Subscription subscription = Subscription.builder()
                .endTime(createSubscriptionParam.getEndTime())
                .startTime(createSubscriptionParam.getStartTime())
                .priceId(createSubscriptionParam.getPriceId())
                .userId(createSubscriptionParam.getUserId())
                .build();
        return subscriptionRepository.save(subscription);
    }

    @Override
    public List<Subscription> getAllSubscription(SubscriptionFilter filter, OrderBy orderBy, Pagination pagination) {
        Specification<Subscription> spec = Specification.where(null);
        if (!CollectionUtils.isEmpty(filter.getIds())) {
            spec = spec.and(((root, query, cb) -> root.get("id").in(filter.getIds())));
        }
        if (filter.getUserId() != null) {
            spec = spec.and(((root, query, cb) -> cb.equal(root.get("userId"), filter.getUserId())));
        }
        if (filter.getPriceId() != null) {
            spec = spec.and(((root, query, cb) -> cb.equal(root.get("priceId"), filter.getPriceId())));
        }

        Sort sort = null;
        if (orderBy != null) {
            Sort.Direction direction = orderBy.getOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            sort = Sort.by(direction, orderBy.getField());
        }

        Pageable pageable = PageRequest.of(pagination.getPage() - 1, pagination.getPageSize(), sort);
        Page<Subscription> pageSubscriptions = subscriptionRepository.findAll(spec, pageable);
        pagination.setTotal(pageSubscriptions.getTotalPages());
        return pageSubscriptions.toList();
    }

    @Override
    public SubscriptionDetail getSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() ->
                new EReaderException("not found subscriptionId"));
        Price price = priceRepository.findById(subscription.getPriceId()).orElseThrow(() ->
                new EReaderException("not found price by id"));
        Plan plan = planRepository.findById(price.getId()).orElseThrow(() ->
                new EReaderException("not found plan by id"));
        PriceDetail priceDetail = PriceDetail.builder()
                .amount(price.getAmount())
                .durationUnit(price.getDurationUnit())
                .currency(price.getCurrency())
                .duration(price.getDuration())
                .effectiveDate(price.getEffectiveDate())
                .features(price.getFeatures())
                .plan(plan)
                .id(price.getId())
                .build();
        return SubscriptionDetail.builder()
                .endTime(subscription.getEndTime())
                .priceDetail(priceDetail)
                .id(subscription.getId())
                .startTime(subscription.getStartTime())
                .build();
    }

    @Override
    public void deleteSubscription(Long id, Long userId) {
        Subscription subscription = subscriptionRepository.findByIdAndUserId(id, userId).orElseThrow(() ->
                new EReaderException("not found Subscription by id and userId"));
        subscriptionRepository.delete(subscription);
    }
}
