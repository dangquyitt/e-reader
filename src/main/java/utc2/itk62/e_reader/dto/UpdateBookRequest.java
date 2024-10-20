package utc2.itk62.e_reader.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateBookRequest {
    private Long id;
    private MultipartFile file;
    private String title;
    private String author;
    private int publishedYear;
    private int totalPage;
    private String genre;
    private String language;
}
