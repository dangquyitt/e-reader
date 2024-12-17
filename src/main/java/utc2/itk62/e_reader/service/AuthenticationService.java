package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.domain.model.UserInfo;

public interface AuthenticationService {
    UserInfo login(String email, String password);

    UserInfo loginWithGoogle(String idTokenString);

    User register(String email, String password);

    void sendVerifyEmail(String email);

    void verifyEmail(String verificationCode);

    void sendResetPassword(String email);

    void resetPassword(String email, String password, String code);
}
