package utc2.itk62.e_reader.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UpdateBookParam {
    private MultipartFile fileBook;
    private MultipartFile fileCoverImage;
    private String title;
    private String description;
    private int totalPage;
    private Double rating;
    private int publishedYear;
    private Long id;
}
