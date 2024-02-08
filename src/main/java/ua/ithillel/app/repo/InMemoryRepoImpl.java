package ua.ithillel.app.repo;

import org.springframework.stereotype.Repository;
import ua.ithillel.app.exeption.AccountNotFoundException;
import ua.ithillel.app.model.Account;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryRepoImpl implements InMemoryRepo {

    private final List<Account> accounts;
    private static int  idSetter = 0;

    public InMemoryRepoImpl() {
        this.accounts = new ArrayList<>();
    }
    @Override
    public Account addAccount(Account account) {
        account.setId(++idSetter);
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

    @Override
    public Account deleteAccount(Integer id) {
        Account accountToDelete = getAccountById(id);
        accounts.remove(accountToDelete);
        return accountToDelete;
    }

    @Override
    public Account editAccount(Integer id, Account account) {
        Account accountToEdit = getAccountById(id);
        accountToEdit.setFirstName(account.getFirstName());
        accountToEdit.setLastName(account.getLastName());
        accountToEdit.setGender(account.getGender());
        accountToEdit.setCountry(account.getCountry());
        accountToEdit.setBalance(account.getBalance());
        return accountToEdit;
    }
}
