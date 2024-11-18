package utc2.itk62.e_reader.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class CreateBookParam {
    private MultipartFile fileBook;
    private MultipartFile fileCoverImage;
    private String title;
    private String desc;
    private int totalPage;
    private float rating;
    private int publishedYear;
}
