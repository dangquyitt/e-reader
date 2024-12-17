package utc2.itk62.e_reader.service;


public interface ResetPasswordService {
    void sendResetPassword(String email);

    void resetPassword(String email, String password, String token);
}
