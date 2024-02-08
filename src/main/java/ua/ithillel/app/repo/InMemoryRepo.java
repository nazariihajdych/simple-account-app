package ua.ithillel.app.repo;

import ua.ithillel.app.model.Account;

import java.util.List;

public interface InMemoryRepo {
    Account addAccount(Account account);
    List<Account> getAllAccounts();
    Account getAccountById(Long id);

    Account deleteAccount(Long id);

    Account editAccount(Long id, Account account);
}
