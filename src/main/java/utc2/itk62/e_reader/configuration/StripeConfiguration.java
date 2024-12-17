package utc2.itk62.e_reader.configuration;

import com.stripe.Stripe;
import com.stripe.StripeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfiguration {
    @Value("${application.stripe.secret-key}")
    private String stripeSecretKey;

    @Bean
    public StripeClient stripeClient() {
        return new StripeClient(stripeSecretKey);
    }
}
