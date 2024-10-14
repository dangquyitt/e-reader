package utc2.itk62.e_reader.dto;

import jakarta.validation.constraints.NotBlank;

public class EnrollBookRequest {
    @NotBlank
    Long bookId;
}
