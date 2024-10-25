package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Plan;
import utc2.itk62.e_reader.dto.PlanResponse;
import utc2.itk62.e_reader.dto.UpdatePlanRequest;
import utc2.itk62.e_reader.service.PlanService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plans")
@AllArgsConstructor
public class PlanController {
    private final PlanService planService;

    @PostMapping
    public ResponseEntity<HTTPResponse<PlanResponse>> create(@RequestBody String name) {
        Plan plan = planService.createPlan(name);
        PlanResponse planResponse = PlanResponse.builder()
                .id(plan.getId())
                .name(plan.getName())
                .build();
        return HTTPResponse.ok(planResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HTTPResponse<PlanResponse>> update(@RequestBody UpdatePlanRequest request) {
        Plan plan = planService.updatePlan(request.getName(), request.getId());
        PlanResponse planResponse = PlanResponse.builder()
                .id(plan.getId())
                .name(plan.getName())
                .build();
        return HTTPResponse.ok(planResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HTTPResponse<String>> deleteBook(@PathVariable Long id) {

        String message = "";
        if(planService.deletePlan(id)){
            message = "Delete book successfully";
        }

        return HTTPResponse.ok(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HTTPResponse<PlanResponse>> getBook(@PathVariable Long id) {
        Plan plan = planService.getPlanById(id);
        PlanResponse planResponse = PlanResponse.builder()
                .name(plan.getName())
                .id(plan.getId())
                .build();
        return HTTPResponse.ok(planResponse);
    }
    @GetMapping
    public ResponseEntity<HTTPResponse<List<PlanResponse>>> getAllBook() {

        List<PlanResponse> planResponses = planService.getAllPlan()
                .stream().map(plan -> PlanResponse.builder()
                        .id(plan.getId())
                        .name(plan.getName())
                        .build()).collect(Collectors.toList());

        return HTTPResponse.ok(planResponses);
    }
}
