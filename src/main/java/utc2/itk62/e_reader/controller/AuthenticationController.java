package utc2.itk62.e_reader.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utc2.itk62.e_reader.component.Translator;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.dto.LoginRequest;
import utc2.itk62.e_reader.dto.LoginResponse;
import utc2.itk62.e_reader.dto.RegisterUserRequest;
import utc2.itk62.e_reader.service.AuthenticationService;
import utc2.itk62.e_reader.service.TokenService;

import java.time.Duration;
import java.util.UUID;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor

public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    private final Translator translator;

    @PostMapping("/login")
    public ResponseEntity<HTTPResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        User user = authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword());
        TokenPayload tokenPayload = new TokenPayload(UUID.randomUUID().toString(), user.getId());
        String token = tokenService.generateAccessToken(tokenPayload);

        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        int duration = Math.toIntExact(Duration
                .between(tokenPayload.getIssuedAt(), tokenPayload.getExpiredAt())
                .getSeconds());
        cookie.setMaxAge(duration);
        response.addCookie(cookie);

        return HTTPResponse.success(translator.translate(MessageCode.LOGIN_SUCCESS, user.getEmail()),new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<HTTPResponse> register(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        User user = authenticationService.register(registerUserRequest.getEmail(), registerUserRequest.getPassword());
        return HTTPResponse.success(translator.translate(MessageCode.REGISTER_SUCCESS));
    }
}
