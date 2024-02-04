package ua.ithillel.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.ithillel.app.model.Account;
import ua.ithillel.app.repo.InMemoryRepo;
import ua.ithillel.app.service.AccountService;

import java.util.Comparator;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final InMemoryRepo inMemoryRepo;
    @Override
    public Account addAccount(Account account) {
        account.setId(inMemoryRepo.getAllAccounts().size() + 1);
        inMemoryRepo.addAccount(account);
        return account;
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

//    @Override
//    public boolean youngerThen(List<Account> accounts, int youngerThenYear) {
//        return accounts.stream()
//                .anyMatch(account -> account.getBirthday().getYear() > youngerThenYear);
//    }

    @Override
    public Double sumOfMaleBalances(List<Account> accounts) {
        return accounts.stream()
                .filter(account -> account.getGender().equals("M"))
                .mapToDouble(Account::getBalance)
                .sum();
    }

//    @Override
//    public Map<String, List<Account>> groupByMonthOfBirth(List<Account> accounts) {
//        return accounts.stream()
//                .collect(Collectors.groupingBy(account -> account.getBirthday().getMonth().toString()));
//    }

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

//    @Override
//    public Account oldestAccount(List<Account> accounts) {
//        return accounts.stream()
//                .min(Comparator.comparing(Account::getBirthday))
//                .orElse(null);
//    }

//    @Override
//    public Map<Integer, Double> groupByBirthYearAndAverageBalance(List<Account> accounts) {
//        return accounts.stream()
//                .collect(Collectors.groupingBy(account -> account.getBirthday().getYear(),
//                        Collectors.averagingDouble(Account::getBalance)));
//    }

    @Override
    public Account longestLastname(List<Account> accounts) {
        return accounts.stream()
                .max(Comparator.comparingInt(account -> account.getLastName().length()))
                .orElse(null);
    }
}
