package utc2.itk62.e_reader.service;

import com.stripe.model.checkout.Session;

public interface PaymentService {
    String generatePaymentURL(Long userId, Long planId);

    void confirmPayment(Session session);
}
