package utc2.itk62.e_reader.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @Email
    @NotBlank
    private String email;
    @Size(min = 6)
    @NotBlank
    private String password;
}
