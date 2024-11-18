package utc2.itk62.e_reader.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utc2.itk62.e_reader.domain.enums.Status;

import java.time.Instant;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "email_verifications")
public class EmailVerification extends BaseEntity{

    private String VerificationCode;
    private String email;
    private Instant expiredAt;

    @Enumerated(EnumType.STRING)
    private Status status;

}
