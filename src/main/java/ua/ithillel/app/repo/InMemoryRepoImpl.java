package ua.ithillel.app.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ua.ithillel.app.model.Account;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InMemoryRepoImpl implements ua.ithillel.app.repo.InMemoryRepo {

    private final List<Account> accounts;
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
                .orElse(null);
    }
}
