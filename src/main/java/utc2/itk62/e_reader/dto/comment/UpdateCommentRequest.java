package utc2.itk62.e_reader.dto.comment;

import lombok.Data;

@Data
public class UpdateCommentRequest {

    private Long id;
    private Long bookId;
    private String content;
}
