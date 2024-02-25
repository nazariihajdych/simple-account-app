package ua.ithillel.app.service;

import ua.ithillel.app.model.Account;
import ua.ithillel.app.model.dto.AccountDTO;

import java.util.List;
import java.util.Set;

public interface AccountService {
    //CRUD
    AccountDTO addAccount(AccountDTO accountDTO);
    List<AccountDTO> getAllAccountsByUserId(Long id);
    AccountDTO getAccountById(Long id);
    AccountDTO deleteAccount(Long id);
    AccountDTO editAccount(Long id, AccountDTO accountDTO);
}
