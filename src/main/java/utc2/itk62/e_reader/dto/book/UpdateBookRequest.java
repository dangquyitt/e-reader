package utc2.itk62.e_reader.dto.book;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateBookRequest {
    private Long id;
    private MultipartFile fileBook;
    private MultipartFile fileCoverImage;
    private String title;
    private String desc;
    private int totalPage;
    private Double rating;
    private int publishedYear;
}
