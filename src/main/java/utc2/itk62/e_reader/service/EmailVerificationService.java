package utc2.itk62.e_reader.service;

public interface EmailVerificationService {
    void verify(String verificationCode);

    void sendEmailVerificationCode(String email);
}
