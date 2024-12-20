package utc2.itk62.e_reader.dto.rating;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteRatingRequest {
    @NotNull
    private Long id;
    @NotNull
    private Long userID;
}
