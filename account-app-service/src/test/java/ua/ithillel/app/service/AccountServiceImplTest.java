package ua.ithillel.app.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.ithillel.app.model.Account;
import ua.ithillel.app.model.User;
import ua.ithillel.app.model.dto.AccountDTO;
import ua.ithillel.app.model.mapper.AccountMapper;
import ua.ithillel.app.model.mapper.PaymentMapper;
import ua.ithillel.app.repo.AccountRepo;
import ua.ithillel.app.repo.UserRepo;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest extends ServiceTestParent{

    @Mock
    AccountRepo accountRepo;
    @Mock
    UserRepo userRepo;
    @Mock
    AccountMapper accountMapper;
    @Mock
    PaymentMapper paymentMapper;
    @InjectMocks
    AccountServiceImpl accountService;

    @Test
    void addAccount_successful() {
        AccountDTO mockAccountDTO = mockAccountDTOs.get(0);
        Account mockAccount = mockAccounts.get(0);

        when(accountMapper.accountDTOToAccount(mockAccountDTO)).thenReturn(mockAccount);
        when(userRepo.findById(mockAccountDTO.getUser_id()))
                .thenReturn(Optional.ofNullable(mockUsers.get(mockAccountDTO.getUser_id().intValue()-1)));
        when(accountRepo.save(mockAccount)).thenReturn(mockAccount);
        when(accountMapper.accountToAccountDTO(mockAccount)).thenReturn(mockAccountDTO);

        AccountDTO savedAccountResult = accountService.addAccount(mockAccountDTO);

        verify(accountMapper, times(1)).accountDTOToAccount(mockAccountDTO);
        verify(userRepo, times(1)).findById(mockAccountDTO.getUser_id());
        verify(accountRepo, times(1)).save(mockAccount);
        verify(accountMapper, times(1)).accountToAccountDTO(mockAccount);

        assertNotNull(savedAccountResult);
        assertNotNull(savedAccountResult.getId());
        assertEquals(savedAccountResult, mockAccountDTO);
    }

    @Test
    void getAllAccountsByUserId_successful() {
        User mockUser = mockUsers.get(0);
        Long userId = 1L;

        when(userRepo.findById(userId)).thenReturn(Optional.ofNullable(mockUser));
        when(accountMapper.accountToAccountDTO(any())).thenReturn(mockAccountDTOs.get(0));

        List<AccountDTO> byUserId = accountService.getAllAccountsByUserId(userId);

        verify(userRepo, times(1)).findById(userId);
        verify(accountMapper, times(mockUser.getAccounts().size())).accountToAccountDTO(any());

        assertNotNull(byUserId);
        assertThat(byUserId.size()).isEqualTo(mockUser.getAccounts().size());
    }

    @Test
    void getAccountById_successful() {
        Account mockAccount = mockAccounts.get(0);
        Long accountId = 1L;

        when(accountRepo.findById(accountId)).thenReturn(Optional.ofNullable(mockAccount));
        when(accountMapper.accountToAccountDTO(mockAccount)).thenReturn(mockAccountDTOs.get(0));

        AccountDTO accountById = accountService.getAccountById(accountId);

        verify(accountRepo, times(1)).findById(accountId);
        verify(accountMapper, times(1)).accountToAccountDTO(mockAccount);

        assertNotNull(accountById);
        assertEquals(accountById.getId(), accountId);
    }

    @Test
    void deleteAccount_successful() {
        Account mockAccount = mockAccounts.get(0);
        User mockUser = mockUsers.get(0);
        mockAccount.setUser(mockUser);
        Long accountId = 1L;

        when(accountRepo.findById(accountId)).thenReturn(Optional.of(mockAccount));
        when(userRepo.findById(mockAccount.getUser().getId())).thenReturn(Optional.ofNullable(mockUser));
        when(userRepo.save(mockUser)).thenReturn(mockUser);
        when(accountMapper.accountToAccountDTO(mockAccount)).thenReturn(mockAccountDTOs.get(0));

        AccountDTO deleteAccount = accountService.deleteAccount(accountId);

        verify(accountRepo, times(1)).findById(accountId);
        verify(userRepo, times(1)).findById(mockAccount.getUser().getId());
        verify(userRepo, times(1)).save(mockUser);
        verify(accountMapper, times(1)).accountToAccountDTO(mockAccount);

        assertNotNull(deleteAccount);
        assertEquals(deleteAccount.getId(), accountId);
    }

    @Test
    void editAccount_successful() {
        Account mockAccount = mockAccounts.get(0);
        AccountDTO mockAccountDTO = mockAccountDTOs.get(0);
        User mockUser = mockUsers.get(0);
        mockAccount.setUser(mockUser);
        Long accountId = 1L;

        when(accountRepo.findById(accountId)).thenReturn(Optional.of(mockAccount));
        when(userRepo.findById(mockAccountDTO.getUser_id())).thenReturn(Optional.ofNullable(mockUser));
        when(accountRepo.save(mockAccount)).thenReturn(mockAccount);
        when(accountMapper.accountToAccountDTO(mockAccount)).thenReturn(mockAccountDTO);

        AccountDTO editAccount = accountService.editAccount(accountId, mockAccountDTO);

        verify(accountRepo, times(1)).findById(accountId);
        verify(userRepo, times(1)).findById(mockAccountDTO.getUser_id());
        verify(accountRepo, times(1)).save(mockAccount);
        verify(accountMapper, times(1)).accountToAccountDTO(mockAccount);

        assertNotNull(editAccount);
    }
}