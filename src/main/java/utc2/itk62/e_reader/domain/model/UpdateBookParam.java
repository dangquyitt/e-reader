package utc2.itk62.e_reader.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import utc2.itk62.e_reader.dto.CreateBookRequest;

@Data
@Builder
public class UpdateBookParam {
    private MultipartFile file;
    private CreateBookRequest request;
    private Long id;
}
