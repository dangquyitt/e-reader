package utc2.itk62.e_reader.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Plan;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.dto.collection.DeleteCollectionRequest;
import utc2.itk62.e_reader.dto.plan.CreatePlanRequest;
import utc2.itk62.e_reader.dto.plan.UpdatePlanRequest;
import utc2.itk62.e_reader.service.PlanService;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;
    private final MessageSource messageSource;

    @GetMapping
    public ResponseEntity<HTTPResponse> getAllPlans() {
        return HTTPResponse.success("success", planService.getAllPlans());
    }

    @PostMapping
    public ResponseEntity<HTTPResponse> createPlan(@Valid @RequestBody CreatePlanRequest createPlanRequest) {
        return null;
    }

    @PutMapping
    public ResponseEntity<HTTPResponse> createPlan(@Valid @RequestBody UpdatePlanRequest updatePlanRequest, Locale locale) {
        Plan plan = planService.updatePlan(updatePlanRequest.getId(), updatePlanRequest.getName());
        String message = messageSource.getMessage("plan.update.success", null, locale);
        return HTTPResponse.success(message, plan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HTTPResponse> deletePlan(@Valid @PathVariable Long id, Locale locale) {
        planService.deletePlan(id);
        return HTTPResponse.success("success");
    }
}
