package ua.ithillel.homework2.service;

import ua.ithillel.homework1.model.Account;

import java.util.List;
import java.util.Set;

public interface AccountService {
    List<Account> balanceMoreThen(List<Account> accounts);

    Set<String> accountsCountries(List<Account> accounts);

    boolean youngerThen(List<Account> accounts);

    int sumOfMaleBalances(List<Account> accounts);

    List<Account> groupByMonthOfBirth(List<Account> accounts);

    int averageBalanceDependOfCountry(List<Account> accounts, String country);

    String nameAndLastnameOfEmployees(List<Account> accounts);

    List<Account> sortByLastnameAndName(List<Account> accounts);

    Account OldestAccount(List<Account> accounts);

    //one more

    Account longestLastname(List<Account> accounts);

}
