package utc2.itk62.e_reader.dto.comment;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateCommentRequest {

    private Long bookId;
    private String content;
}
