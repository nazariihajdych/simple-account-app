package ua.ithillel.app.service;

import ua.ithillel.app.model.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO addAccount(AccountDTO accountDTO);
    List<AccountDTO> getAllAccountsByUserId(Long id);
    AccountDTO getAccountById(Long id);
    void deleteAccount(Long id);
    AccountDTO editAccount(Long id, AccountDTO accountDTO);
}
