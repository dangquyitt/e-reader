package utc2.itk62.e_reader.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateBookRequest {

    private MultipartFile fileBook;
    private MultipartFile fileCoverImage;
    private String title;
    private String desc;
    private int totalPage;
    private float rating;
    private int publishedYear;
}
