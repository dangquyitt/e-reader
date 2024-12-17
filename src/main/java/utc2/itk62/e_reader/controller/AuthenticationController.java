package utc2.itk62.e_reader.controller;

import com.google.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.ApiResource;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
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
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Value("${application.client.url}")
    private String CLIENT_URL;
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    private final Translator translator;
    private final StripeClient stripeClient;

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

    @PostMapping("/googleLogin")
    public ResponseEntity<HTTPResponse> loginWithGoogle(@Valid @RequestBody GoogleLoginRequest googleLoginRequest, Locale locale) {
        UserInfo userInfo = authenticationService.loginWithGoogle(googleLoginRequest.getIdTokenString());
        TokenPayload tokenPayload = new TokenPayload(UUID.randomUUID().toString(), userInfo.getId(), userInfo.getRoles());
        String token = tokenService.generateAccessToken(tokenPayload);
        return HTTPResponse.success(translator.translate(locale, MessageCode.LOGIN_SUCCESS, userInfo.getEmail()), new LoginResponse(token));
    }


    @PostMapping("/checkout")
    public ResponseEntity<HTTPResponse> checkout() {
        // Set your secret key. Remember to switch to your live secret key in production!
        try {
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder().setPriceData(
                                                    SessionCreateParams.LineItem.PriceData.builder()
                                                            .setCurrency("usd")
                                                            .setUnitAmount(2000L)
                                                            .setProductData(
                                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                            .setName("T-shirt")
                                                                            .setDescription("Comfortable cotton t-shirt")
                                                                            .build())
                                                            .build()).setQuantity(1L)
                                            .build()).setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl("https://example.com/success")
                            .setCancelUrl("https://example.com/cancel")
                            .build();

            Session session = stripeClient.checkout().sessions().create(params);
            return HTTPResponse.success(session.getId(), session.getUrl());
        } catch (StripeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/portalSession")
    public ResponseEntity<HTTPResponse> portalSession(@RequestParam String sessionId, Locale locale) {
        Stripe.apiKey = "sk_test_51QWDv6Hs8vR6Dv5voZxnR4JhqGtS7jdtkLZ41Q5COTaiU5p9eAA1nKfc3e14r6L2TU8GwPMYtTagFaP4bjpep7F8008GTjX3Dv";
        try {
            // For demonstration purposes, we're using the Checkout session to retrieve the
            // customer ID.
            // Typically this is stored alongside the authenticated user in your database.
            // Deserialize request from our front end.
            Session checkoutSession = Session.retrieve(sessionId);

            String customer = checkoutSession.getCustomer();
            // Authenticate your user.
            com.stripe.param.billingportal.SessionCreateParams params = new com.stripe.param.billingportal.SessionCreateParams.Builder()
                    .setReturnUrl(CLIENT_URL).setCustomer(customer).build();

            com.stripe.model.billingportal.Session portalSession = com.stripe.model.billingportal.Session.create(params);


            return HTTPResponse.success(portalSession.getId(), portalSession.getUrl());
        } catch (StripeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/webhook")
    public ResponseEntity<HTTPResponse> webhook(HttpEntity<String> httpEntity, @RequestHeader Map<String, String> headers) {
        String payload = httpEntity.getBody();
        Event event = null;
        try {
            event = ApiResource.GSON.fromJson(payload, Event.class);
        } catch (JsonSyntaxException e) {
            // Invalid payload
            System.out.println("⚠️  Webhook error while parsing basic request.");
            return ResponseEntity.badRequest().build();
        }
//        String sigHeader = headers.get("Stripe-Signature");
//        if (endpointSecret != null && sigHeader != null) {
//            // Only verify the event if you have an endpoint secret defined.
//            // Otherwise use the basic event deserialized with GSON.
//            try {
//                event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
//            } catch (SignatureVerificationException e) {
//                // Invalid signature
//                System.out.println("⚠️  Webhook error while validating signature.");
//                response.status(400);
//                return "";
//            }
//        }
        // Deserialize the nested object inside the event
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            // Deserialization failed, probably due to an API version mismatch.
            // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
            // instructions on how to handle this case, or return an error here.
        }
        // Handle the event
        Subscription subscription = null;
        switch (event.getType()) {
            case "customer.subscription.deleted":
                subscription = (Subscription) stripeObject;
                // Then define and call a function to handle the event
                // customer.subscription.deleted
                // handleSubscriptionTrialEnding(subscription);
            case "customer.subscription.trial_will_end":
                subscription = (Subscription) stripeObject;
                // Then define and call a function to handle the event
                // customer.subscription.trial_will_end
                // handleSubscriptionDeleted(subscriptionDeleted);
            case "customer.subscription.created":
                subscription = (Subscription) stripeObject;
                // Then define and call a function to handle the event
                // customer.subscription.created
                // handleSubscriptionCreated(subscription);
            case "customer.subscription.updated":
                subscription = (Subscription) stripeObject;
                // Then define and call a function to handle the event
                // customer.subscription.updated
                // handleSubscriptionUpdated(subscription);
            case "entitlements.active_entitlement_summary.updated":
                subscription = (Subscription) stripeObject;
                // Then define and call a function to handle the event
                // entitlements.active_entitlement_summary.updated
                // handleEntitlementUpdated(subscription);
                // ... handle other event types
            default:
                System.out.println("Unhandled event type: " + event.getType());
        }
        return null;
    }
}
