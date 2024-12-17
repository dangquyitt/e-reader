package utc2.itk62.e_reader.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import utc2.itk62.e_reader.domain.entity.Role;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Data
public class TokenPayload {
    private String id;
    private Long userId;
    private Instant issuedAt;
    private Instant expiredAt;
    private List<Role> roles;

    public TokenPayload(String id, Long userId, List<Role> roles) {
        this.id = id;
        this.userId = userId;
        this.roles = roles;
    }
}
