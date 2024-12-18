package utc2.itk62.e_reader.dto.plan;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePlanRequest {
    @NotBlank
    private String name;
}
