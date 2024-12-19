package utc2.itk62.e_reader.dto.favorite;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteFavoriteRequest {
    @NotNull
    private Long bookId;
}
