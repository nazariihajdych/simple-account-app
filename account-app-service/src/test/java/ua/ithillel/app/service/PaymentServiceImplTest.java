package ua.ithillel.app.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.ithillel.app.model.Account;
import ua.ithillel.app.model.Payment;
import ua.ithillel.app.model.dto.PaymentDTO;
import ua.ithillel.app.model.mapper.PaymentMapper;
import ua.ithillel.app.repo.AccountRepo;
import ua.ithillel.app.repo.PaymentRepo;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest extends ServiceTestParent{

    @Mock
    PaymentRepo paymentRepo;
    @Mock
    PaymentMapper paymentMapper;
    @Mock
    AccountRepo accountRepo;
    @Mock
    UserService userService;

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Test
    void addPayment() {
        PaymentDTO mockPaymentDTO = mockPaymentDTOs.get(0);
        Payment mockPayment = mockPayments.get(0);
        Account mockAccount = mockAccounts.get(0);

        when(paymentMapper.paymentDTOToPayment(mockPaymentDTO)).thenReturn(mockPayment);
        when(accountRepo.findById(mockPaymentDTO.getAccountId())).thenReturn(Optional.of(mockAccount));
        when(paymentRepo.save(mockPayment)).thenReturn(mockPayment);
        when(paymentMapper.paymentToPaymentDTO(mockPayment)).thenReturn(mockPaymentDTO);

        PaymentDTO addedPayment = paymentService.addPayment(mockPaymentDTO);

        verify(paymentMapper, times(1)).paymentDTOToPayment(mockPaymentDTO);
        verify(accountRepo, times(1)).findById(mockPaymentDTO.getAccountId());
        verify(paymentRepo, times(1)).save(mockPayment);
        verify(paymentMapper, times(1)).paymentToPaymentDTO(mockPayment);

        assertNotNull(addedPayment);
        assertNotNull(addedPayment.getId());
        assertEquals(addedPayment, mockPaymentDTO);
    }

    @Test
    void getPaymentById() {
        Payment mockPayment = mockPayments.get(0);
        PaymentDTO mockPaymentDTO = mockPaymentDTOs.get(0);
        Long paymentId = 1L;

        when(paymentRepo.findById(paymentId)).thenReturn(Optional.of(mockPayment));
        when(paymentMapper.paymentToPaymentDTO(mockPayment)).thenReturn(mockPaymentDTO);

        PaymentDTO paymentById = paymentService.getPaymentById(paymentId);

        verify(paymentRepo, times(1)).findById(paymentId);
        verify(paymentMapper, times(1)).paymentToPaymentDTO(mockPayment);

        assertNotNull(paymentById);
        assertEquals(paymentById.getId(), paymentId);
    }

    @Test
    void getAllPaymentsByAccountId() {
        Account mockAccount = mockAccounts.get(0);
        Long accountId = 1L;

        when(accountRepo.findById(accountId)).thenReturn(Optional.of(mockAccount));
        when(paymentMapper.paymentToPaymentDTO(mockPayments.get(0))).thenReturn(mockPaymentDTOs.get(0));

        List<PaymentDTO> allPaymentsByAccountId = paymentService.getAllPaymentsByAccountId(accountId);

        verify(accountRepo, times(1)).findById(accountId);
        verify(paymentMapper, times(mockAccount.getPayments().size())).paymentToPaymentDTO(mockPayments.get(0));

        assertNotNull(allPaymentsByAccountId);
        assertThat(allPaymentsByAccountId.size()).isEqualTo(mockAccount.getPayments().size());
    }

    @Test
    void editPayment() {
        PaymentDTO mockPaymentDTO = mockPaymentDTOs.get(0);
        Payment mockPayment = mockPayments.get(0);
        Account mockAccount = mockAccounts.get(0);
        Long paymentId = 1L;

        when(paymentRepo.findById(paymentId)).thenReturn(Optional.of(mockPayment));
        when(accountRepo.findById(mockPaymentDTO.getAccountId())).thenReturn(Optional.of(mockAccount));
        when(paymentRepo.save(mockPayment)).thenReturn(mockPayment);
        when(paymentMapper.paymentToPaymentDTO(mockPayment)).thenReturn(mockPaymentDTO);

        PaymentDTO editedPayment = paymentService.editPayment(paymentId, mockPaymentDTO);

        verify(paymentRepo, times(1)).findById(paymentId);
        verify(accountRepo, times(1)).findById(mockPaymentDTO.getAccountId());
        verify(paymentRepo, times(1)).save(mockPayment);
        verify(paymentMapper, times(1)).paymentToPaymentDTO(mockPayment);

        assertNotNull(editedPayment);
    }

    @Test
    void deletePayment() {
        Payment mockPayment = mockPayments.get(0);
        Account mockAccount = mockAccounts.get(0);
        mockPayment.setAccount(mockAccount);
        Long paymentId = 1L;

        when(paymentRepo.findById(paymentId)).thenReturn(Optional.of(mockPayment));
        when(accountRepo.findById(mockPayment.getAccount().getId())).thenReturn(Optional.of(mockAccount));
        when(accountRepo.save(mockAccount)).thenReturn(mockAccount);
        when(paymentMapper.paymentToPaymentDTO(mockPayment)).thenReturn(mockPaymentDTOs.get(0));

        PaymentDTO deletePayment = paymentService.deletePayment(paymentId);

        verify(paymentRepo, times(1)).findById(paymentId);
        verify(accountRepo, times(1)).findById(mockPayment.getAccount().getId());
        verify(accountRepo, times(1)).save(mockAccount);
        verify(paymentMapper, times(1)).paymentToPaymentDTO(mockPayment);

        assertNotNull(deletePayment);
    }
}