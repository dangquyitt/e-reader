package utc2.itk62.e_reader.dto;

import lombok.Data;

@Data
public class CreateBookRequest {
    private String title;
    private String author;
    private int publishedYear;
    private int totalPage;
    private String genre;
    private String language;
}
