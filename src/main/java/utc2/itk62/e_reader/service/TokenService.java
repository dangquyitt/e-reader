package utc2.itk62.e_reader.service;


import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.exception.TokenException;

public interface TokenService {
    String generateAccessToken(TokenPayload payload) throws TokenException;

    TokenPayload validateAccessToken(String token) throws TokenException;
}
