package ua.ithillel.app.repo;

import ua.ithillel.app.model.Account;

import java.util.List;

public interface InMemoryRepo {
    Account addAccount(Account account);
    List<Account> getAllAccounts();
    Account getAccountById(Integer id);
}
