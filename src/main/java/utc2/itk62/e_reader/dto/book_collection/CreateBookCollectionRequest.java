package utc2.itk62.e_reader.dto.book_collection;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBookCollectionRequest {
    @NotNull
    private Long collectionId;
    @NotNull
    private Long bookId;
}
