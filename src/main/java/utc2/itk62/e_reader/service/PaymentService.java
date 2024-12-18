package utc2.itk62.e_reader.service;

public interface PaymentService {
    String generatePaymentURL(Long userId, Long planId);
}
