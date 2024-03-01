package ua.ithillel.app.service;

import org.springframework.stereotype.Service;
import ua.ithillel.app.exeption.AccountNotFoundException;
import ua.ithillel.app.exeption.UserNotFoundException;
import ua.ithillel.app.model.Account;
import ua.ithillel.app.model.User;
import ua.ithillel.app.model.dto.AccountDTO;
import ua.ithillel.app.model.mapper.AccountMapper;
import ua.ithillel.app.model.mapper.PaymentMapper;
import ua.ithillel.app.repo.AccountRepo;
import ua.ithillel.app.repo.UserRepo;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;
    private final UserRepo userRepo;
    private final AccountRepo accountRepo;
    private final PaymentMapper paymentMapper;

    public AccountServiceImpl(AccountMapper accountMapper, UserRepo userRepo, AccountRepo accountRepo, PaymentMapper paymentMapper) {
        this.accountMapper = accountMapper;
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public AccountDTO addAccount(AccountDTO accountDTO) {
        Account account = accountMapper.accountDTOToAccount(accountDTO);
        User user = getUserOrThrow(accountDTO.getUser_id());
        account.setUser(user);
        Account save = accountRepo.save(account);
        return accountMapper.accountToAccountDTO(save);
    }

    @Override
    public List<AccountDTO> getAllAccountsByUserId(Long id) {
        User user = getUserOrThrow(id);
        return user.getAccounts().stream()
                .map(accountMapper::accountToAccountDTO)
                .toList();
    }

    @Override
    public AccountDTO getAccountById(Long id) {
        Account account = getAccountOrThrow(id);
        return accountMapper.accountToAccountDTO(account);
    }

    @Override
    public AccountDTO deleteAccount(Long id) {
        Account accountById = getAccountOrThrow(id);

        User user = getUserOrThrow(accountById.getUser().getId());
        user.getAccounts().removeIf(account -> account.getId().equals(id));
        userRepo.save(user);
        return accountMapper.accountToAccountDTO(accountById);
    }

    @Override
    public AccountDTO editAccount(Long id, AccountDTO accountDTO) {
        Account account = getAccountOrThrow(id);
        account.setFirstName(accountDTO.getFirstName());
        account.setLastName(accountDTO.getLastName());
        account.setDateOfBirth(accountDTO.getDateOfBirth());
        account.setCountry(accountDTO.getCountry());
        account.setGender(accountDTO.getGender());
        if (accountDTO.getPayments() != null) {
            account.setPayments(accountDTO.getPayments().stream()
                    .map(paymentMapper::paymentDTOToPayment)
                    .toList());
        }

        User user = getUserOrThrow(accountDTO.getUser_id());
        account.setUser(user);
        Account save = accountRepo.save(account);
        return accountMapper.accountToAccountDTO(save);
    }

    private User getUserOrThrow(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + id + "not found"));
    }

    private Account getAccountOrThrow(Long id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with id = " + id + "not found"));
    }
}
