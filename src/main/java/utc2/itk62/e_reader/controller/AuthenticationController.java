package utc2.itk62.e_reader.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.model.JwtAuthenticationToken;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.dto.LoginRequest;
import utc2.itk62.e_reader.dto.LoginResponse;
import utc2.itk62.e_reader.dto.RegisterUserRequest;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.service.AuthenticationService;
import utc2.itk62.e_reader.service.TokenService;

import java.util.UUID;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<HTTPResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return HTTPResponse.ok(new LoginResponse(
                tokenService.generateAccessToken(new TokenPayload(UUID.randomUUID().toString(), user.getId()))
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<HTTPResponse<Long>> register(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        User user = authenticationService.register(registerUserRequest.getEmail(), registerUserRequest.getPassword());
        return HTTPResponse.ok(user.getId());
    }

}
