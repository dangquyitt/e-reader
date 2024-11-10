package utc2.itk62.e_reader.service.impl;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.component.Translator;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.exception.TokenException;
import utc2.itk62.e_reader.service.TokenService;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {
    @Value("${token.access.secret}")
    private String accessTokenSecret;

    @Value("${token.access.duration}")
    private Duration accessTokenDuration;

    @Override
    public String generateAccessToken(TokenPayload payload) {

        Instant now = Instant.now();
        Instant expiration = now.plus(accessTokenDuration);

        payload.setIssuedAt(now);
        payload.setExpiredAt(expiration);

        return Jwts.builder()
                .issuedAt(Date.from(payload.getIssuedAt()))
                .expiration(Date.from(payload.getExpiredAt()))
                .id(payload.getId())
                .claim("userId", payload.getUserId())
                .signWith(secretKey()).compact();
    }

    @Override
    public TokenPayload validateAccessToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(secretKey())
                    .build()
                    .parseSignedClaims(token).getPayload();
            return new TokenPayload(
                    claims.getId(),
                    claims.get("userId", Long.class),
                    claims.getIssuedAt().toInstant(),
                    claims.getExpiration().toInstant()
            );
        } catch (JwtException e) {
            log.error("JwtException | {}", e.getMessage());
            throw new TokenException(e.getMessage());
        }
    }


    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(accessTokenSecret.getBytes(StandardCharsets.UTF_8));
    }
}
