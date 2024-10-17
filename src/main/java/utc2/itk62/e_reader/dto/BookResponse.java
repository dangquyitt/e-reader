package utc2.itk62.e_reader.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private int year;
    private int numberOfPages;
    private String genre;
    private String language;
    private String file;
}
