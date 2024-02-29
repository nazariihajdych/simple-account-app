package ua.ithillel.app.service;

import ua.ithillel.app.model.dto.PaymentDTO;

import java.util.List;

public interface PaymentService {
    PaymentDTO addPayment(PaymentDTO paymentDTO);

    PaymentDTO getPaymentById(Long id);

    List<PaymentDTO> getAllPaymentsByAccountId(Long accountId);

    List<PaymentDTO> getAllPaymentsByUserId(Long userId);

    PaymentDTO editPayment(Long id, PaymentDTO paymentDTO);

    PaymentDTO deletePayment(Long id);
}
