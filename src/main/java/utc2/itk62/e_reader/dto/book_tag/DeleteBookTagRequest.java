package utc2.itk62.e_reader.dto.book_tag;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteBookTagRequest {
    @NotNull
    private Long bookId;
    @NotNull
    private Long tagId;
}
