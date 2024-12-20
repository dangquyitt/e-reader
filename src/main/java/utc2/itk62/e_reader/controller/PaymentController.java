package utc2.itk62.e_reader.controller;

import com.google.gson.JsonSyntaxException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.ApiResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/{planId}")
    public ResponseEntity<HTTPResponse> generatePaymentURL(@PathVariable Long planId, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        return HTTPResponse.success("success", paymentService.generatePaymentURL(tokenPayload.getUserId(), planId));
    }

    @PostMapping("/webhook")
    public ResponseEntity<HTTPResponse> handleWebhook(@RequestBody String payload) {
        Event event = null;
        try {
            event = ApiResource.GSON.fromJson(payload, Event.class);
        } catch (JsonSyntaxException e) {
            log.error("JsonSyntaxException | {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        // Deserialize the nested object inside the event
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            // Deserialization failed, probably due to an API version mismatch.
            // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
            // instructions on how to handle this case, or return an error here.
            log.error("Deserialization failed");
            return ResponseEntity.badRequest().build();
        }

        // Handle the event
        switch (event.getType()) {
            case "checkout.session.completed":
                Session session = (Session) stripeObject;
                paymentService.confirmPayment(session);
                break;
            default:
                log.info("Unhandled event type: {}", event.getType());
        }
        return HTTPResponse.success("success");
    }

}
