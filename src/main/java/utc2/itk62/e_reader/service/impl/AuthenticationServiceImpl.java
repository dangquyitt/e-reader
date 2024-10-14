package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.exception.CustomException;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.repository.UserRepository;
import utc2.itk62.e_reader.service.AuthenticationService;
import utc2.itk62.e_reader.service.TokenService;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException()
                        .setInternalMessage(String.format("login | not found email. %s", email)));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException().setInternalMessage("login | password mismatch");
        }

        return user;
    }

    @Override
    public User register(String email, String password) {
        userRepository.findByEmail(email)
                .ifPresent(value -> {
                    throw new CustomException().addError(new Error("email", "user.email.exists"));
                });
        User user = User.builder().email(email).password(passwordEncoder.encode(password)).build();
        userRepository.save(user);
        return user;
    }
}
