package utc2.itk62.e_reader.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateBookRequest {
    private String title;
    private String author;
    private int year;
    private int numberOfPages;
    private String genre;
    private String language;
}
