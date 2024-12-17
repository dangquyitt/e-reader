package utc2.itk62.e_reader.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.service.EmailVerificationService;


@RestController
@AllArgsConstructor
@RequestMapping("/api/emailVerifications")
public class EmailVerificationController {
    private final EmailVerificationService emailVerificationService;

    @GetMapping("/verify")
    public ResponseEntity<HTTPResponse> verificationCode(@RequestParam String verificationCode) {
        emailVerificationService.verify(verificationCode);
        // TODO: create message
        return HTTPResponse.success("Verify successfully");
    }
}
