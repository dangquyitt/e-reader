package utc2.itk62.e_reader.service;

public interface EmailVerificationService {
    boolean verifyEmail(String verificationCode);
}