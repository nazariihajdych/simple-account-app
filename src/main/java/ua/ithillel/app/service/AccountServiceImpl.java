package ua.ithillel.app.service;

import org.springframework.stereotype.Service;
import ua.ithillel.app.exeption.AccountNotFoundException;
import ua.ithillel.app.model.Account;
import ua.ithillel.app.repo.InMemoryRepo;

import java.util.Comparator;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final InMemoryRepo inMemoryRepo;

    public AccountServiceImpl(InMemoryRepo inMemoryRepo) {
        this.inMemoryRepo = inMemoryRepo;
    }

    @Override
    public Account addAccount(Account account) {
        return inMemoryRepo.addAccount(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return inMemoryRepo.getAllAccounts();
    }

    @Override
    public Account getAccountById(Integer id) {
        return inMemoryRepo.getAccountById(id);
    }

    @Override
    public Account deleteAccount(Integer id) {
        return inMemoryRepo.deleteAccount(id);
    }

    @Override
    public Account editAccount(Integer id, Account account) {
        return inMemoryRepo.editAccount(id, account);
    }

    @Override
    public List<Account> balanceMoreThen(Double moreThen) {
        return inMemoryRepo.getAllAccounts().stream()
                .filter(account -> account.getBalance() > moreThen)
                .toList();
    }

    @Override
    public Set<String> accountsCountries() {
        return inMemoryRepo.getAllAccounts().stream()
                .map(Account::getCountry)
                .collect(Collectors.toSet());
    }

    @Override
    public Double sumOfMaleBalances(List<Account> accounts) {
        return accounts.stream()
                .filter(account -> account.getGender().equals("M"))
                .mapToDouble(Account::getBalance)
                .sum();
    }

    @Override
    public Double averageBalanceByCountry(String country) {
        return inMemoryRepo.getAllAccounts().stream()
                .filter(account -> account.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.averagingDouble(Account::getBalance));
    }

    @Override
    public String nameAndLastnameOfEmployees(List<List<Account>> accountsList) {
        return accountsList.stream()
                .flatMap(Collection::stream)
                .map(account -> account.getFirstName() + " " + account.getLastName())
                .collect(Collectors.joining(", "));
    }

    @Override
    public List<Account> sortByLastnameAndName(List<Account> accounts) {
        return accounts.stream()
                .sorted(Comparator.comparing(Account::getLastName).thenComparing(Account::getFirstName))
                .toList();
    }

    @Override
    public Account longestLastname(List<Account> accounts) {
        return accounts.stream()
                .max(Comparator.comparingInt(account -> account.getLastName().length()))
                .orElseThrow(() -> new AccountNotFoundException("There isn't account with longest Lastname"));
    }
}
