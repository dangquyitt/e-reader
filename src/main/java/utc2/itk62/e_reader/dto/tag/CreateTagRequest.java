package utc2.itk62.e_reader.dto.tag;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTagRequest {
    @NotNull
    private String name;
}
