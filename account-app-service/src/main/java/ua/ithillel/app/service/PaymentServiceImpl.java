package ua.ithillel.app.service;

import org.springframework.stereotype.Service;
import ua.ithillel.app.exeption.PaymentNotFoundException;
import ua.ithillel.app.model.Payment;
import ua.ithillel.app.model.dto.AccountDTO;
import ua.ithillel.app.model.dto.PaymentDTO;
import ua.ithillel.app.model.dto.UserDTO;
import ua.ithillel.app.model.mapper.AccountMapper;
import ua.ithillel.app.model.mapper.PaymentMapper;
import ua.ithillel.app.repo.PaymentRepo;

import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;
    private final PaymentMapper paymentMapper;
    private final AccountService accountService;
    private final UserService userService;
    private final AccountMapper accountMapper;

    public PaymentServiceImpl(PaymentRepo paymentRepo, PaymentMapper paymentMapper, AccountService accountService, UserService userService, AccountMapper accountMapper) {
        this.paymentRepo = paymentRepo;
        this.paymentMapper = paymentMapper;
        this.accountService = accountService;
        this.userService = userService;
        this.accountMapper = accountMapper;
    }

    @Override
    public PaymentDTO addPayment(PaymentDTO paymentDTO) {
        Payment payment = paymentMapper.paymentDTOToPayment(paymentDTO);
        Payment saved = paymentRepo.save(payment);
        return paymentMapper.paymentToPaymentDTO(saved);
    }

    @Override
    public PaymentDTO getPaymentById(Long id) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with id = " + id + "not found"));
        return paymentMapper.paymentToPaymentDTO(payment);
    }

    @Override
    public List<PaymentDTO> getAllPaymentsByAccountId(Long accountId) {
        AccountDTO accountById = accountService.getAccountById(accountId);
        return accountById.getPayments();
    }

    @Override
    public List<PaymentDTO> getAllPaymentsByUserId(Long userId) {
        List<AccountDTO> accountsByUserId = userService.getUserById(userId).getAccounts();
        return accountsByUserId.stream()
                .flatMap(accountDTO -> accountDTO.getPayments().stream())
                .toList();
    }

    @Override
    public PaymentDTO editPayment(Long id, PaymentDTO paymentDTO) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with id = " + id + "not found"));
        payment.setAmount(paymentDTO.getAmount());
        payment.setAccount(accountMapper.accountDTOToAccount(accountService.getAccountById(paymentDTO.getAccount_id())));
        paymentRepo.save(payment);
        return paymentMapper.paymentToPaymentDTO(payment);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepo.deleteById(id);
    }
}
