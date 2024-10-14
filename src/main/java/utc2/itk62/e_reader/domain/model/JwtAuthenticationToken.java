package utc2.itk62.e_reader.domain.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final TokenPayload payload;

    public JwtAuthenticationToken(TokenPayload payload) {
        super(null);
        this.payload = payload;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return payload;
    }
}
