package utc2.itk62.e_reader.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UpdateBookParam {
    private MultipartFile file;
    private String title;
    private String author;
    private int publishedYear;
    private int totalPage;
    private String genre;
    private String language;
    private Long id;
}
