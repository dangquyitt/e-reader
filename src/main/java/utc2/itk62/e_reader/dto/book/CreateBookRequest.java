package utc2.itk62.e_reader.dto.book;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CreateBookRequest {

    private MultipartFile fileBook;
    private MultipartFile fileCoverImage;
    private String title;
    private String description;
    private int totalPage;
    private int publishedYear;
    private List<Long> tagIds;
}
