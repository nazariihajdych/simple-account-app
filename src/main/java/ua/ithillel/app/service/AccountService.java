package ua.ithillel.app.service;

import ua.ithillel.app.model.Account;

import java.util.List;
import java.util.Set;

public interface AccountService {
    //CRUD
    Account addAccount(Account account);
    List<Account> getAllAccounts();
    Account getAccountById(Long id);
    Account deleteAccount(Long id);
    Account editAccount(Long id, Account account);

    //Others
    List<Account> balanceMoreThen(Double moreThen);

    Set<String> accountsCountries();

    Double sumOfMaleBalances(List<Account> accounts);

    Double averageBalanceByCountry (String country);

    String nameAndLastnameOfEmployees(List<List<Account>> accountsList);

    List<Account> sortByLastnameAndName(List<Account> accounts);

    Account longestLastname(List<Account> accounts);

}
