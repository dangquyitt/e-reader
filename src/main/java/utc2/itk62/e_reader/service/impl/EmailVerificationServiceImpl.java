package utc2.itk62.e_reader.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utc2.itk62.e_reader.constant.EmailVerificationStatus;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.domain.entity.EmailVerification;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.EmailVerificationRepository;
import utc2.itk62.e_reader.repository.UserRepository;
import utc2.itk62.e_reader.service.EmailVerificationService;
import utc2.itk62.e_reader.service.MailService;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {
    @Value("${application.client.url}")
    private String CLIENT_URL;
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final MailService mailService;

    @Override
    @Transactional
    public void verify(String verificationCode) {
        EmailVerification emailVerification = emailVerificationRepository.findByVerificationCode(verificationCode).orElseThrow(() -> new EReaderException(MessageCode.EMAIL_VERIFICATION_CODE_INVALID));

        if (EmailVerificationStatus.USED.equals(emailVerification.getStatus())) {
            throw new EReaderException(MessageCode.EMAIL_VERIFICATION_STATUS_USED);
        }

        if (EmailVerificationStatus.EXPIRED.equals(emailVerification.getStatus())) {
            throw new EReaderException(MessageCode.EMAIL_VERIFICATION_CODE_EXPIRED);
        }

        if (emailVerification.getExpiredAt().isBefore(Instant.now())) {
            emailVerification.setStatus(EmailVerificationStatus.EXPIRED);
            emailVerificationRepository.save(emailVerification);
            throw new EReaderException(MessageCode.EMAIL_VERIFICATION_CODE_EXPIRED);
        }

        if (!verificationCode.equals(emailVerification.getVerificationCode())) {
            throw new EReaderException(MessageCode.EMAIL_VERIFICATION_CODE_INVALID);
        }

        User user = userRepository.findByEmail(emailVerification.getEmail()).orElseThrow(() -> new EReaderException(MessageCode.USER_EMAIL_NOT_EXISTS));

        if (user.getEmailVerifiedAt() != null) {
            emailVerificationRepository.updateStatusByEmail(emailVerification.getEmail(), EmailVerificationStatus.EXPIRED);
            throw new EReaderException(MessageCode.USER_ALREADY_VERIFIED);
        }

        user.setEmailVerifiedAt(Instant.now());
        emailVerification.setStatus(EmailVerificationStatus.USED);
        emailVerificationRepository.save(emailVerification);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void sendEmailVerificationCode(String email) {
        emailVerificationRepository.updateStatusByEmail(email, EmailVerificationStatus.EXPIRED);
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(email);
        emailVerification.setVerificationCode(UUID
                .randomUUID().toString());
        emailVerification.setStatus(EmailVerificationStatus.PENDING);
        emailVerification.setExpiredAt(Instant.now().plus(Duration.ofDays(1)));
        emailVerificationRepository.save(emailVerification);
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("verificationURL", CLIENT_URL + "/emailVerification?verificationCode=" + emailVerification.getVerificationCode());
        mailService.send(email, "E-Reader email verification", "emailVerification.ftlh", dataModel);
        emailVerification.setStatus(EmailVerificationStatus.SENT);
        emailVerificationRepository.save(emailVerification);
    }
}
