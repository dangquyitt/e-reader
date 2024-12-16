package utc2.itk62.e_reader.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.GeneralSecurityException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.constant.RoleName;
import utc2.itk62.e_reader.domain.entity.*;
import utc2.itk62.e_reader.domain.model.UserInfo;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.RoleRepository;
import utc2.itk62.e_reader.repository.UserRepository;
import utc2.itk62.e_reader.repository.UserRoleRepository;
import utc2.itk62.e_reader.service.AuthenticationService;
import utc2.itk62.e_reader.service.EmailVerificationService;
import utc2.itk62.e_reader.service.ResetPasswordService;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final GoogleIdTokenVerifier googleIdTokenVerifier;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailVerificationService emailVerificationService;
    private final ResetPasswordService resetPasswordService;

    @Override
    public UserInfo login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("AuthenticationServiceImpl | email: {} not exists", email);
                    return new EReaderException(MessageCode.USER_CREDENTIALS_INVALID);
                });

        if (user.getPassword() == null) {
            throw new EReaderException(MessageCode.USER_CREDENTIALS_INVALID);
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.error("AuthenticationServiceImpl | password invalid");
            throw new EReaderException(MessageCode.USER_CREDENTIALS_INVALID);
        }

        if (user.getEmailVerifiedAt() == null) {
            throw new EReaderException(MessageCode.USER_NOT_VERIFIED);
        }

        List<Role> roles = roleRepository.findAllByUserId(user.getId());
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(user.getEmail());
        userInfo.setId(user.getId());
        userInfo.setRoles(roles);
        return userInfo;
    }

    @Override
    public UserInfo loginWithGoogle(String idTokenString) {
        try {
            GoogleIdToken idToken = googleIdTokenVerifier.verify(idTokenString);
            if (idToken == null) {
                throw new EReaderException(MessageCode.USER_CREDENTIALS_INVALID);
            }
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            if (!emailVerified) {
                throw new EReaderException(MessageCode.USER_CREDENTIALS_INVALID);
            }
            Optional<User> userOp = userRepository.findByEmail(email);
            if (userOp.isEmpty()) {
                userOp = Optional.of(User.builder().email(email).emailVerifiedAt(Instant.now()).build());
                userRepository.save(userOp.get());
            }
            User user = userOp.get();
            List<Role> roles = roleRepository.findAllByUserId(user.getId());
            UserInfo userInfo = new UserInfo();
            userInfo.setEmail(user.getEmail());
            userInfo.setId(user.getId());
            userInfo.setRoles(roles);
            return userInfo;
        } catch (GeneralSecurityException e) {
            log.error("AuthenticationServiceImpl | exception", e);
            throw new EReaderException(MessageCode.USER_CREDENTIALS_INVALID);
        } catch (IOException e) {
            log.error("AuthenticationServiceImpl | IOException", e);
            throw new EReaderException(MessageCode.USER_CREDENTIALS_INVALID);
        }
    }

    @Override
    public User register(String email, String password) {
        userRepository.findByEmail(email)
                .ifPresent(value -> {
                    log.error("AuthenticationServiceImpl | email: {} already exists", email);
                    throw new EReaderException(MessageCode.USER_EMAIL_EXISTS);
                });
        User user = User.builder().email(email).password(passwordEncoder.encode(password)).build();
        userRepository.save(user);
        Role role = roleRepository.findByName(RoleName.USER).orElseThrow(() -> new EReaderException(MessageCode.INVALID_ROLE_NAME));
        userRoleRepository.save(new UserRole(user.getId(), role.getId()));
        Thread.startVirtualThread(() -> {
            emailVerificationService.sendEmailVerificationCode(user.getEmail());
        });

        return user;
    }

    @Override
    public void sendVerifyEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("AuthenticationServiceImpl | {} ", email);
                    return new EReaderException(MessageCode.USER_EMAIL_NOT_EXISTS);
                });

        if (user.getEmailVerifiedAt() != null) {
            throw new EReaderException(MessageCode.USER_ALREADY_VERIFIED);
        }

        Thread.startVirtualThread(() -> {
            emailVerificationService.sendEmailVerificationCode(user.getEmail());
        });
    }

    @Override
    public void verifyEmail(String verificationCode) {
        emailVerificationService.verify(verificationCode);
    }

    @Override
    @Transactional
    public void sendResetPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("AuthenticationServiceImpl | {} ", email);
                    return new EReaderException(MessageCode.USER_EMAIL_NOT_EXISTS);
                });
        resetPasswordService.sendResetPassword(user.getEmail());
    }

    @Override
    public void resetPassword(String email, String password, String code) {
        resetPasswordService.resetPassword(email, password, code);
    }
}
