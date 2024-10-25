package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.PlanVersion;
import utc2.itk62.e_reader.domain.model.CreatePlanVersionParam;
import utc2.itk62.e_reader.domain.model.UpdatePlanVersionParam;
import utc2.itk62.e_reader.dto.CreatePlanVersionRequest;
import utc2.itk62.e_reader.dto.PlanResponse;
import utc2.itk62.e_reader.dto.PlanVersionResponse;
import utc2.itk62.e_reader.dto.UpdatePlanVersionRequest;
import utc2.itk62.e_reader.service.PlanVersionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/planversions")
@AllArgsConstructor
public class PlanVersionController {
    private final PlanVersionService planVersionService;

    @PostMapping
    public ResponseEntity<HTTPResponse<PlanVersionResponse>> create(@RequestBody CreatePlanVersionRequest request) {
        CreatePlanVersionParam createPlanVersionParam = CreatePlanVersionParam.builder()
                .planId(request.getPlanId())
                .effectiveTime(request.getEffectiveTime())
                .maxEnrollBook(request.getMaxEnrollBook())
                .price(request.getPrice())
                .build();
        PlanVersion planVersion = planVersionService.createPlanVersion(createPlanVersionParam);
        PlanResponse planResponse = PlanResponse.builder()
                .name(planVersion.getPlan().getName())
                .id(planVersion.getPlan().getId())
                .build();
        PlanVersionResponse planVersionResponse = PlanVersionResponse.builder()
                .id(planVersion.getId())
                .effectiveTime(planVersion.getEffectiveTime())
                .planResponse(planResponse)
                .maxEnrollBook(planVersion.getMaxEnrollBook())
                .price(planVersion.getPrice())
                .build();
        return HTTPResponse.ok(planVersionResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HTTPResponse<PlanVersionResponse>> update(@RequestBody UpdatePlanVersionRequest request) {
        UpdatePlanVersionParam updatePlanVersionParam = UpdatePlanVersionParam.builder()
                .planVersionId(request.getPlanVersionId())
                .planId(request.getPlanId())
                .effectiveTime(request.getEffectiveTime())
                .maxEnrollBook(request.getMaxEnrollBook())
                .price(request.getPrice())
                .build();
        PlanVersion planVersion = planVersionService.updatePlanVersion(updatePlanVersionParam);
        PlanResponse planResponse = PlanResponse.builder()
                .name(planVersion.getPlan().getName())
                .id(planVersion.getPlan().getId())
                .build();
        PlanVersionResponse planVersionResponse = PlanVersionResponse.builder()
                .id(planVersion.getId())
                .effectiveTime(planVersion.getEffectiveTime())
                .planResponse(planResponse)
                .maxEnrollBook(planVersion.getMaxEnrollBook())
                .price(planVersion.getPrice())
                .build();
        return HTTPResponse.ok(planVersionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HTTPResponse<String>> deleteBook(@PathVariable Long id) {

        String message = "";
        if(planVersionService.deletePlanVersion(id)){
            message = "Delete book successfully";
        }

        return HTTPResponse.ok(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HTTPResponse<PlanVersionResponse>> getBook(@PathVariable Long id) {
        PlanVersion planVersion = planVersionService.getById(id);
        PlanResponse planResponse = PlanResponse.builder()
                .name(planVersion.getPlan().getName())
                .id(planVersion.getPlan().getId())
                .build();
        PlanVersionResponse planVersionResponse = PlanVersionResponse.builder()
                .id(planVersion.getId())
                .price(planVersion.getPrice())
                .maxEnrollBook(planVersion.getMaxEnrollBook())
                .planResponse(planResponse)
                .effectiveTime(planVersion.getEffectiveTime())
                .build();
        return HTTPResponse.ok(planVersionResponse);
    }
    @GetMapping
    public ResponseEntity<HTTPResponse<List<PlanVersionResponse>>> getAllBook() {

        List<PlanVersionResponse> planVersionResponses = planVersionService.getAllPlanVersion()
                .stream().map(planVersion -> PlanVersionResponse.builder()
                        .id(planVersion.getId())
                        .effectiveTime(planVersion.getEffectiveTime())
                        .price(planVersion.getPrice())
                        .planResponse(PlanResponse.builder()
                                .name(planVersion.getPlan().getName())
                                .id(planVersion.getId())
                                .build())
                        .maxEnrollBook(planVersion.getMaxEnrollBook())
                        .build()).collect(Collectors.toList());

        return HTTPResponse.ok(planVersionResponses);
    }

}
