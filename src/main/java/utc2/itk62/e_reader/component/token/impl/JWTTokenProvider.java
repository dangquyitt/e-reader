package utc2.itk62.e_reader.component.token.impl;

import utc2.itk62.e_reader.component.token.TokenProvider;

public class JWTTokenProvider implements TokenProvider {

    @Override
    public String generateAccessToken(String userId) {
        return null;
    }

    @Override
    public String generateRefreshToken() {
        return null;
    }

    @Override
    public String validateAccessToken(String token) {
        return null;
    }

    @Override
    public String validateRefreshToken(String token) {
        return null;
    }
}
