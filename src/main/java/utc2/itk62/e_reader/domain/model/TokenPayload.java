package utc2.itk62.e_reader.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@AllArgsConstructor
@Data
public class TokenPayload {
    private String id;
    private Long userId;
    private Instant issuedAt;
    private Instant expiredAt;
    public TokenPayload(String id, Long userId) {
        this.id = id;
        this.userId = userId;
    }
}
