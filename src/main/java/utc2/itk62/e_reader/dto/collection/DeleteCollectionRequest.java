package utc2.itk62.e_reader.dto.collection;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteCollectionRequest {
    @NotNull
    private Long id;
}
