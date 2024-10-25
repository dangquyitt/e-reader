package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Subscription;
import utc2.itk62.e_reader.dto.CreateSubscriptionRequest;
import utc2.itk62.e_reader.dto.SubscriptionResponse;
import utc2.itk62.e_reader.service.SubscriptionService;

@RestController
@RequestMapping("/api/subscriptions")
@AllArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<HTTPResponse<SubscriptionResponse>> create(@RequestBody CreateSubscriptionRequest request) {

        Subscription subscription = subscriptionService.createSubscription(request.getUserId(), request.getPlanId());
        SubscriptionResponse subscriptionResponse = SubscriptionResponse.builder()
                .id(subscription.getId())
                .userId(subscription.getUser().getId())
                .startTime(subscription.getStartTime())
                .endTime(subscription.getEndTime())
                .planId(subscription.getPlan().getId())
                .build();

        return HTTPResponse.ok(subscriptionResponse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HTTPResponse<String>> delete(@PathVariable Long id) {

        String message = "";
        if(subscriptionService.deleteSubscription(id)){
            message = "Delete book successfully";
        }

        return HTTPResponse.ok(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HTTPResponse<SubscriptionResponse>> getBook(@PathVariable Long id) {
        Subscription subscription = subscriptionService.getById(id);
        SubscriptionResponse subscriptionResponse = SubscriptionResponse.builder()
                .id(subscription.getId())
                .planId(subscription.getPlan().getId())
                .userId(subscription.getUser().getId())
                .endTime(subscription.getEndTime())
                .startTime(subscription.getStartTime())
                .build();

        return HTTPResponse.ok(subscriptionResponse);
    }

}
