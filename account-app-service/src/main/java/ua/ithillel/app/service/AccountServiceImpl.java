package ua.ithillel.app.service;

import org.springframework.stereotype.Service;
import ua.ithillel.app.exeption.AccountNotFoundException;
import ua.ithillel.app.exeption.UserNotFoundException;
import ua.ithillel.app.model.Account;
import ua.ithillel.app.model.User;
import ua.ithillel.app.model.dto.AccountDTO;
import ua.ithillel.app.model.enums.Gender;
import ua.ithillel.app.model.mapper.AccountMapper;
import ua.ithillel.app.repo.AccountRepo;
import ua.ithillel.app.repo.UserRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final AccountMapper accountMapper;
    private final UserRepo userRepo;

    public AccountServiceImpl(AccountRepo accountRepo, AccountMapper accountMapper, UserRepo userRepo) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
        this.userRepo = userRepo;
    }

    @Override
    public AccountDTO addAccount(AccountDTO accountDTO) {
        Account account = accountMapper.accountDTOToAccount(accountDTO);
        User user = userRepo.findById(accountDTO.getUser_id())
                .orElseThrow(() -> new UserNotFoundException("User with id = " + accountDTO.getUser_id() + "not found"));
        account.setUser(user);
        Account added = accountRepo.addAccount(account);
        return accountMapper.accountToAccountDTO(added);
    }

    @Override
    public List<AccountDTO> getAllAccountsByUserId(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + id + "not found"));
        return user.getAccounts().stream()
                .map(accountMapper::accountToAccountDTO)
                .toList();
    }
    @Override
    public AccountDTO getAccountById(Long id) {
        return accountMapper.accountToAccountDTO(accountRepo.getAccountById(id));
    }

    @Override
    public AccountDTO deleteAccount(Long id) {
        return accountMapper.accountToAccountDTO(accountRepo.deleteAccount(id));
    }

    @Override
    public AccountDTO editAccount(Long id, AccountDTO accountDTO) {
        Account account = accountMapper.accountDTOToAccount(accountDTO);
        Account editedAccount = accountRepo.editAccount(id, account);
        return accountMapper.accountToAccountDTO(editedAccount);
    }
}
