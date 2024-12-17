package utc2.itk62.e_reader.dto.auth;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private String password;
    private String token;
}
