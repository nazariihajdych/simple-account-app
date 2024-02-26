package ua.ithillel.app.service;

import org.springframework.stereotype.Service;
import ua.ithillel.app.exeption.AccountNotFoundException;
import ua.ithillel.app.exeption.PaymentNotFoundException;
import ua.ithillel.app.model.Account;
import ua.ithillel.app.model.Payment;
import ua.ithillel.app.model.dto.AccountDTO;
import ua.ithillel.app.model.dto.PaymentDTO;
import ua.ithillel.app.model.mapper.PaymentMapper;
import ua.ithillel.app.repo.AccountRepo;
import ua.ithillel.app.repo.PaymentRepo;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;
    private final PaymentMapper paymentMapper;
    private final AccountRepo accountRepo;
    private final UserService userService;

    public PaymentServiceImpl(PaymentRepo paymentRepo, PaymentMapper paymentMapper, AccountRepo accountRepo, UserService userService) {
        this.paymentRepo = paymentRepo;
        this.paymentMapper = paymentMapper;
        this.accountRepo = accountRepo;
        this.userService = userService;
    }

    @Override
    public PaymentDTO addPayment(PaymentDTO paymentDTO) {
        Payment payment = paymentMapper.paymentDTOToPayment(paymentDTO);
        Account account = getAccountOrThrow(paymentDTO.getAccountId());
        payment.setAccount(account);
        Payment saved = paymentRepo.save(payment);
        return paymentMapper.paymentToPaymentDTO(saved);
    }

    @Override
    public PaymentDTO getPaymentById(Long id) {
        Payment payment = getPaymentOrThrow(id);
        return paymentMapper.paymentToPaymentDTO(payment);
    }

    @Override
    public List<PaymentDTO> getAllPaymentsByAccountId(Long accountId) {
        Account account = getAccountOrThrow(accountId);
        return account.getPayments().stream()
                .map(paymentMapper::paymentToPaymentDTO)
                .toList();
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
        Payment payment = getPaymentOrThrow(id);
        payment.setAmount(paymentDTO.getAmount());
        payment.setAccount(getAccountOrThrow(paymentDTO.getAccountId()));
        paymentRepo.save(payment);
        return paymentMapper.paymentToPaymentDTO(payment);
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = getPaymentOrThrow(id);
        Account account = getAccountOrThrow(payment.getAccount().getId());
        account.getPayments().removeIf(payment1 -> payment1.getId().equals(id));
        accountRepo.save(account);
    }

    private Account getAccountOrThrow(Long accountId) {
        return accountRepo.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with id = " + accountId + "not found"));
    }

    private Payment getPaymentOrThrow(Long id) {
        return paymentRepo.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with id = " + id + "not found"));
    }
}
