package utc2.itk62.e_reader.service;


import utc2.itk62.e_reader.domain.model.TokenPayload;

public interface TokenService {
    String generateAccessToken(TokenPayload payload);

    TokenPayload validateAccessToken(String token);

    boolean revokeToken(String token);
}
