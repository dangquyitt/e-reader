package utc2.itk62.e_reader.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Subscription;
import utc2.itk62.e_reader.domain.model.CreateSubscriptionParam;
import utc2.itk62.e_reader.domain.model.SubscriptionFilter;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.dto.RequestFilter;
import utc2.itk62.e_reader.dto.subscription.CreateSubscriptionRequest;
import utc2.itk62.e_reader.dto.subscription.DeleteSubscriptionRequest;
import utc2.itk62.e_reader.service.SubscriptionService;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/subscriptions")
@AllArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final MessageSource messageSource;

    @PostMapping
    public ResponseEntity<HTTPResponse> create(@RequestBody CreateSubscriptionRequest request, Locale locale, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        request.setUserId(tokenPayload.getUserId());
        CreateSubscriptionParam createSubscriptionParam = CreateSubscriptionParam.builder()
                .endTime(request.getEndTime())
                .priceId(request.getPriceId())
                .startTime(request.getStartTime())
                .userId(request.getUserId())
                .build();
        Subscription subscription = subscriptionService.addSubscription(createSubscriptionParam);

        String message = messageSource.getMessage("subscription.create.success", null, locale);
        return HTTPResponse.success(message, subscription);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HTTPResponse> getSubscription(@PathVariable Long id) {
        return HTTPResponse.success(subscriptionService.getSubscription(id));
    }

    @PostMapping("/filter")
    public ResponseEntity<HTTPResponse> getAllSubscription(@RequestBody RequestFilter<SubscriptionFilter> filter) {
        List<Subscription> resp = subscriptionService.
                getAllSubscription(filter.getFilter(), filter.getOrderBy(), filter.getPagination());
        return HTTPResponse.success("Subscriptions retrieved successfully", resp, filter.getPagination());
    }

    @DeleteMapping
    public ResponseEntity<HTTPResponse> deleteSubscription(@Valid @RequestBody DeleteSubscriptionRequest deleteSubscriptionRequest, Locale locale, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        subscriptionService.deleteSubscription(deleteSubscriptionRequest.getId(), tokenPayload.getUserId());
        return HTTPResponse.success("Subscription delete successfully");
    }
}
