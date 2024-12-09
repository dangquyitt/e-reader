package utc2.itk62.e_reader.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utc2.itk62.e_reader.domain.enums.EmailVerificationStatus;

import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "email_verifications")
public class EmailVerification extends BaseEntity {

    @Column(name = "verification_code", nullable = false)
    private String VerificationCode;

    @Column(nullable = false)
    private String email;

    private Instant expiredAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailVerificationStatus status;

}
