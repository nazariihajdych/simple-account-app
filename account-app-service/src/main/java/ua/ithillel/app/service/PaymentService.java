package ua.ithillel.app.service;

import org.springframework.stereotype.Service;
import ua.ithillel.app.model.dto.PaymentDTO;
import ua.ithillel.app.repo.PaymentRepo;

import java.util.List;

@Service
public interface PaymentService {
    PaymentDTO addPayment(PaymentDTO paymentDTO);

    PaymentDTO getPaymentById(Long id);

    List<PaymentDTO> getAllPaymentsByAccountId(Long accountId);

    List<PaymentDTO> getAllPaymentsByUserId(Long userId);

    PaymentDTO editPayment(Long id, PaymentDTO paymentDTO);

    void deletePayment(Long id);
}
