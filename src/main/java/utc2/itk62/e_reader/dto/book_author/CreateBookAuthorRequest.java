package utc2.itk62.e_reader.dto.book_author;

import lombok.Data;

@Data
public class CreateBookAuthorRequest {

    Long bookId;
    Long authorId;
}
