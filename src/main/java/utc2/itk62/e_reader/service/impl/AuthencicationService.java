package utc2.itk62.e_reader.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.dto.AuthenticationRequest;
import utc2.itk62.e_reader.exception.CustomException;
import utc2.itk62.e_reader.repository.IUserRepository;
import utc2.itk62.e_reader.service.IAuthenticationService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthencicationService implements IAuthenticationService {

    private final IUserRepository iUserRepository;
    @Override
    public boolean authenticate(AuthenticationRequest request) {
        var user = iUserRepository.findByEmail(request.getUsername()).orElseThrow(() -> {
            var exception = new CustomException().setStatus(404)
                    .addError(new Error("username", "User not existed"));
            log.error("CustomException thrown with errors: {}", exception.getErrors());
            return exception;
        });
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }
}
