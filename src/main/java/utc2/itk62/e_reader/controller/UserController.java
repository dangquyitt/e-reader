package utc2.itk62.e_reader.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utc2.itk62.e_reader.component.Translator;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.dto.user.ChangePasswordRequest;
import utc2.itk62.e_reader.service.UserService;

import java.util.Locale;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final Translator translator;

    @PostMapping("/changePassword")
    public ResponseEntity<HTTPResponse> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, Locale locale, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        userService.changePassword(tokenPayload.getUserId(), changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
        return HTTPResponse.success(translator.translate(locale, MessageCode.CHANGE_PASSWORD_SUCCESS));
    }
}
