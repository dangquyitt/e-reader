package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Subscription;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.dto.CreateSubscriptionRequest;
import utc2.itk62.e_reader.dto.PlanResponse;
import utc2.itk62.e_reader.dto.SubscriptionResponse;
import utc2.itk62.e_reader.exception.CustomException;
import utc2.itk62.e_reader.repository.UserRepository;
import utc2.itk62.e_reader.service.SubscriptionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subscriptions")
@AllArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<HTTPResponse<SubscriptionResponse>> create(@RequestBody CreateSubscriptionRequest request) {
        TokenPayload tokenPayload = (TokenPayload) SecurityContextHolder
                .getContext()
                .getAuthentication().getPrincipal();

        Subscription subscription = subscriptionService.createSubscription(tokenPayload.getUserId(), request.getPlanId());
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
    public ResponseEntity<HTTPResponse<SubscriptionResponse>> getSubscription(@PathVariable Long id) {

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

    @GetMapping
    public ResponseEntity<HTTPResponse<List<SubscriptionResponse>>> getAllSubscription() {
        TokenPayload tokenPayload = (TokenPayload) SecurityContextHolder
                .getContext()
                .getAuthentication().getPrincipal();
        User user = userRepository.findById(tokenPayload.getUserId()).orElseThrow(() ->
                new CustomException().addError(new Error("id","user.id.not_found")));

        List<SubscriptionResponse> subscriptionResponses = subscriptionService.getAll(user).stream()
                .map(subscription -> SubscriptionResponse.builder()
                        .id(subscription.getId())
                        .planId(subscription.getPlan().getId())
                        .userId(subscription.getUser().getId())
                        .endTime(subscription.getEndTime())
                        .startTime(subscription.getStartTime())
                        .build()).collect(Collectors.toList());


        return HTTPResponse.ok(subscriptionResponses);
    }
}
