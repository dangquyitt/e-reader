package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.exception.DuplicateException;
import utc2.itk62.e_reader.exception.InvalidCredentialException;
import utc2.itk62.e_reader.repository.UserRepository;
import utc2.itk62.e_reader.service.AuthenticationService;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialException(email));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialException(user.getPassword());
        }

        return user;
    }

    @Override
    public User register(String email, String password) {
        userRepository.findByEmail(email)
                .ifPresent(value -> {
                    throw new DuplicateException("email", "user.email.exists");
                });
        User user = User.builder().email(email).password(passwordEncoder.encode(password)).build();
        userRepository.save(user);
        return user;
    }
}
