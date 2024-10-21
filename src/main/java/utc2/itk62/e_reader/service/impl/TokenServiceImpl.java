package utc2.itk62.e_reader.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.exception.CustomException;
import utc2.itk62.e_reader.service.TokenService;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
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
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(accessTokenDuration)))
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
        } catch (Exception e) {
            throw new CustomException()
                    .addError(new Error("accessToken", "token.access_token.invalid"))
                    .setException(e);
        }
    }

    @Override
    public boolean revokeToken(String token) {
        return false;
    }

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(accessTokenSecret.getBytes(StandardCharsets.UTF_8));
    }
}
