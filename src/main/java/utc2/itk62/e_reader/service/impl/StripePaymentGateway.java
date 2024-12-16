package utc2.itk62.e_reader.service.impl;

import com.stripe.StripeClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StripePaymentGateway {
    private StripeClient stripeClient;

    
}
