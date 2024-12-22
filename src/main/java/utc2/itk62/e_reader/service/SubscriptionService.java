package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Collection;
import utc2.itk62.e_reader.domain.entity.Subscription;
import utc2.itk62.e_reader.domain.model.CollectionFilter;
import utc2.itk62.e_reader.domain.model.CreateSubscriptionParam;
import utc2.itk62.e_reader.domain.model.OrderBy;
import utc2.itk62.e_reader.domain.model.SubscriptionFilter;
import utc2.itk62.e_reader.dto.subscription.SubscriptionDetail;

import java.util.List;

public interface SubscriptionService {

    Subscription addSubscription(CreateSubscriptionParam createSubscriptionParam);

    List<Subscription> getAllSubscription(SubscriptionFilter filter, OrderBy orderBy, Pagination pagination);

    SubscriptionDetail getSubscription(Long id);

    void deleteSubscription(Long id, Long userId);
}
