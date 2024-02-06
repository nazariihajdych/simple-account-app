package ua.ithillel.app.repo;

import org.springframework.stereotype.Repository;
import ua.ithillel.app.exeption.AccountNotFoundException;
import ua.ithillel.app.model.Account;

import java.util.List;

@Repository
public class InMemoryRepoImpl implements InMemoryRepo {

    private final List<Account> accounts;

    public InMemoryRepoImpl(List<Account> accounts) {
        this.accounts = accounts;
    }
    @Override
    public Account addAccount(Account account) {
        accounts.add(account);
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accounts;
    }

    @Override
    public Account getAccountById(Integer id) {
        return accounts.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("Account with id = " + id + " not found"));
    }
}
