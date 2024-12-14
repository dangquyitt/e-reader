package utc2.itk62.e_reader.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ResendVerifyRequest {
    @NotEmpty
    @Email
    private String email;
}
