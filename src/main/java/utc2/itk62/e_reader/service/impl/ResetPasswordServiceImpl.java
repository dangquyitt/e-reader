package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utc2.itk62.e_reader.constant.EmailVerificationStatus;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.constant.ResetPasswordStatus;
import utc2.itk62.e_reader.domain.entity.EmailVerification;
import utc2.itk62.e_reader.domain.entity.ResetPasswordRequest;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.ResetPasswordRequestRepository;
import utc2.itk62.e_reader.repository.UserRepository;
import utc2.itk62.e_reader.service.MailService;
import utc2.itk62.e_reader.service.ResetPasswordService;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {
    private final UserRepository userRepository;
    private final ResetPasswordRequestRepository resetPasswordRequestRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    @Transactional
    public void sendResetPassword(String email) {
        resetPasswordRequestRepository.updateStatusByEmail(email, EmailVerificationStatus.EXPIRED);
        ResetPasswordRequest rq = new ResetPasswordRequest();
        rq.setEmail(email);
        rq.setToken(UUID.randomUUID().toString());
        rq.setStatus(ResetPasswordStatus.PENDING);
        rq.setExpiredAt(Instant.now().plus(Duration.ofDays(1)));
        resetPasswordRequestRepository.save(rq);
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("resetPasswordURL", "http://localhost:8080/api/auth/resetPassword?token=" + rq.getToken());
        mailService.send(email, "E-Reader reset password request", "resetPasswordRequest.ftlh", dataModel);
        rq.setStatus(ResetPasswordStatus.SENT);
        resetPasswordRequestRepository.save(rq);
    }

    @Override
    public void resetPassword(String email, String password, String token) {
        ResetPasswordRequest rpr = resetPasswordRequestRepository.findByToken(token).orElseThrow(() -> new EReaderException(""));

        if (ResetPasswordStatus.COMPLETED.equals(rpr.getStatus())) {
            throw new EReaderException(MessageCode.RESET_PASSWORD_ALREADY_COMPLETED);
        }

        if (ResetPasswordStatus.EXPIRED.equals(rpr.getStatus())) {
            throw new EReaderException(MessageCode.RESET_PASSWORD_EXPIRED);
        }

        if (rpr.getExpiredAt().isBefore(Instant.now())) {
            rpr.setStatus(ResetPasswordStatus.EXPIRED);
            resetPasswordRequestRepository.save(rpr);
            throw new EReaderException(MessageCode.RESET_PASSWORD_EXPIRED);
        }

        if (!token.equals(rpr.getToken())) {
            throw new EReaderException(MessageCode.RESET_PASSWORD_INCORRECT);
        }

        User user = userRepository.findByEmail(email).orElseThrow(() -> new EReaderException(MessageCode.USER_EMAIL_NOT_EXISTS));
        user.setPassword(passwordEncoder.encode(password));
        rpr.setStatus(ResetPasswordStatus.COMPLETED);
        resetPasswordRequestRepository.save(rpr);
        userRepository.save(user);
    }
}
