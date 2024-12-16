package utc2.itk62.e_reader.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.service.PlanService;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @GetMapping
    public ResponseEntity<HTTPResponse> getAllPlans() {
        return HTTPResponse.success("success", planService.getAllPlans());
    }
}
