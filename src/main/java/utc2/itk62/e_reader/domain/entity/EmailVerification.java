package utc2.itk62.e_reader.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "email_verifications", schema = "public", indexes = {
        @Index(name = "email_verifications_email_idx", columnList = "email")
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "id", nullable = false)),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false))
})
public class EmailVerification extends BaseEntity {
    @NotNull
    @Column(name = "verification_code", nullable = false, length = Integer.MAX_VALUE)
    private String verificationCode;

    @NotNull
    @Column(name = "email", nullable = false, length = Integer.MAX_VALUE)
    private String email;

    @Column(name = "expired_at")
    private Instant expiredAt;

    @NotNull
    @ColumnDefault("'PENDING'")
    @Column(name = "status", nullable = false, length = Integer.MAX_VALUE)
    private String status;

}