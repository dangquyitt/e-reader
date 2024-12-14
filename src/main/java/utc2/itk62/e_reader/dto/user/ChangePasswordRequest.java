package utc2.itk62.e_reader.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @Size(min = 6)
    @NotBlank
    private String oldPassword;
    @Size(min = 6)
    @NotBlank
    private String newPassword;
}
