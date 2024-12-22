package utc2.itk62.e_reader.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class CreateBookParam {
    private MultipartFile fileBook;
    private MultipartFile fileCoverImage;
    private String title;
    private String desc;
    private int totalPage;
    private Double rating;
    private int publishedYear;
    private List<Long> tagIds;
}
