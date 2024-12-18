package utc2.itk62.e_reader.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/{planId}")
    public ResponseEntity<HTTPResponse> generatePaymentURL(@PathVariable Long planId, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        return HTTPResponse.success("success", paymentService.generatePaymentURL(tokenPayload.getUserId(), planId));
    }

}
