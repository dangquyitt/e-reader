package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.domain.entity.EmailVerification;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.domain.enums.EmailVerificationStatus;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.EmailVerificationRepository;
import utc2.itk62.e_reader.repository.UserRepository;
import utc2.itk62.e_reader.service.EmailVerificationService;

import java.time.Instant;

@Service
@AllArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;

    @Override
    public boolean verifyEmail(String verificationCode) {
        // TODO: create message code
        EmailVerification emailVerification = emailVerificationRepository.findByVerificationCode(verificationCode).orElseThrow(() -> new EReaderException(""));

        if (emailVerification.getStatus() == EmailVerificationStatus.USED || emailVerification.getStatus() == EmailVerificationStatus.EXPIRED) {
            // TODO: create message code
            throw new EReaderException("");
        }

        if (!verificationCode.equals(emailVerification.getVerificationCode())) {
            // TODO: create message code
            throw new EReaderException("");
        }


        // TODO: create message code
        User user = userRepository.findByEmail(emailVerification.getEmail()).orElseThrow(() -> new EReaderException(""));

        if (user.getEmailVerifiedAt() != null) {
            // TODO: create message code
            throw new EReaderException("");
        }

        user.setEmailVerifiedAt(Instant.now());
        userRepository.save(user);

        return true;
    }
}
