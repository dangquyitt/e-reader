package utc2.itk62.e_reader.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.dto.CreateUserRequest;
import utc2.itk62.e_reader.exception.CustomException;
import utc2.itk62.e_reader.model.User;
import utc2.itk62.e_reader.repository.IUserRepository;
import utc2.itk62.e_reader.service.IUserService;

import java.time.LocalDateTime;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Long createUser(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail()))
            throw new CustomException()
                    .setStatus(404)
                    .addError(new Error("email","user.email.existed"));
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user).getId();
    }
}
