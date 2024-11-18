package utc2.itk62.e_reader.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {

    private Long id;
    private String title;
    private String desc;
    private int totalPage;
    private float rating;
    private int publishedYear;
    private String coverImageUrl;
    private String fileUrl;
}
