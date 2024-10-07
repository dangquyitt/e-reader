package utc2.itk62.e_reader.component.token;

public interface TokenProvider {
    String generateAccessToken(String userId);
    String generateRefreshToken();
    String validateAccessToken(String token);
    String validateRefreshToken(String token);

}
