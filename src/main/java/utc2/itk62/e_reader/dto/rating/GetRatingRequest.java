package utc2.itk62.e_reader.dto.rating;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetRatingRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long bookId;

}
