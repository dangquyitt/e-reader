package utc2.itk62.e_reader.service;

public interface UserService {
    void changePassword(Long userId, String oldPassword, String newPassword);
}
