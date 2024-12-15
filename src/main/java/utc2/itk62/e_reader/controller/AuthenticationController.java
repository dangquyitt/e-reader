package utc2.itk62.e_reader.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.component.Translator;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.domain.model.UserInfo;
import utc2.itk62.e_reader.dto.auth.*;
import utc2.itk62.e_reader.service.AuthenticationService;
import utc2.itk62.e_reader.service.TokenService;

import java.util.Locale;
import java.util.UUID;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor

public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    private final Translator translator;

    @PostMapping("/login")
    public ResponseEntity<HTTPResponse> login(@Valid @RequestBody LoginRequest loginRequest, Locale locale) {
        UserInfo userInfo = authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword());
        TokenPayload tokenPayload = new TokenPayload(UUID.randomUUID().toString(), userInfo.getId(), userInfo.getRoles());
        String token = tokenService.generateAccessToken(tokenPayload);
        return HTTPResponse.success(translator.translate(locale, MessageCode.LOGIN_SUCCESS, userInfo.getEmail()), new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<HTTPResponse> register(@Valid @RequestBody RegisterUserRequest registerUserRequest, Locale locale) {
        User user = authenticationService.register(registerUserRequest.getEmail(), registerUserRequest.getPassword());
        return HTTPResponse.success(translator.translate(locale, MessageCode.REGISTER_SUCCESS));
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<HTTPResponse> verificationCode(@RequestParam String verificationCode, Locale locale) {
        authenticationService.verifyEmail(verificationCode);
        return HTTPResponse.success(translator.translate(locale, MessageCode.EMAIL_VERIFICATION_SUCCESS));
    }

    @PostMapping("/verifyEmail/send")
    public ResponseEntity<HTTPResponse> resend(@RequestBody EmailRequest request) {
        authenticationService.sendVerifyEmail(request.getEmail());
        // TODO: create message code
        return HTTPResponse.success("Resend verification code successfully");
    }

    @PostMapping("/resetPassword/send")
    public ResponseEntity<HTTPResponse> resetPassword(@RequestBody EmailRequest email, Locale locale) {
        authenticationService.sendResetPassword(email.getEmail());
        return HTTPResponse.success(translator.translate(locale, MessageCode.RESET_PASSWORD_REQUEST_SUCCESS));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<HTTPResponse> resetPassword(@RequestBody ResetPasswordRequest request, Locale locale) {
        authenticationService.resetPassword(request.getEmail(), request.getPassword(), request.getToken());
        return HTTPResponse.success(translator.translate(locale, MessageCode.RESET_PASSWORD_SUCCESS));
    }

    @PostMapping("googleLogin")
    public ResponseEntity<HTTPResponse> loginWithGoogle(@Valid @RequestBody GoogleLoginRequest googleLoginRequest, Locale locale) {
        UserInfo userInfo = authenticationService.loginWithGoogle(googleLoginRequest.getIdTokenString());
        TokenPayload tokenPayload = new TokenPayload(UUID.randomUUID().toString(), userInfo.getId(), userInfo.getRoles());
        String token = tokenService.generateAccessToken(tokenPayload);
        return HTTPResponse.success(translator.translate(locale, MessageCode.LOGIN_SUCCESS, userInfo.getEmail()), new LoginResponse(token));
    }
}
