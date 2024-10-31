package utc2.itk62.e_reader.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @Email(message = "user.email.invalid")
    @NotBlank(message = "user.email.empty")
    private String email;
    @Size(min = 6, message = "user.password.min")
    @NotBlank(message = "user.password.empty")
    private String password;
}
