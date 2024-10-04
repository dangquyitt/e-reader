package utc2.itk62.e_reader.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.core.response.AuthenticationResponse;
import utc2.itk62.e_reader.dto.AuthenticationRequest;
import utc2.itk62.e_reader.exception.CustomException;
import utc2.itk62.e_reader.repository.IUserRepository;
import utc2.itk62.e_reader.service.IAuthenticationService;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthencicationService implements IAuthenticationService {

    private final IUserRepository iUserRepository;
    @NonFinal
    protected static final String SIGNER_KEY = "jYpmvBjb1zSKIJMVDm3mJ0UtDRWCpluI2xzcDzECgnBL2zBl9q1pPnQ5HkP5OeHq";
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var user = iUserRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new CustomException().setStatus(404)
                        .addError(new Error("username","user not existed")));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!authenticated)
            throw new CustomException().setStatus(401).addError(new Error("Unauthenticated","Unauthenticated"));

        var token = generateToken(request.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();

    }

    private String generateToken(String username){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("devUTC2.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(Duration.ofHours(1)).toEpochMilli()))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header,payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }

    }
}
