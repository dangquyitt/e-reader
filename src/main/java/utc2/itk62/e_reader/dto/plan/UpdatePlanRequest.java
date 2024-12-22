package utc2.itk62.e_reader.dto.plan;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePlanRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
}
