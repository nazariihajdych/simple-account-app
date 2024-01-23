package ua.ithillel.homework2.service;

import ua.ithillel.homework1.model.Account;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AccountService {
    List<Account> balanceMoreThen(List<Account> accounts, Double moreThen);

    Set<String> accountsCountries(List<Account> accounts);

    boolean youngerThen(List<Account> accounts, int youngerThenYear);

    Double sumOfMaleBalances(List<Account> accounts);

    Map<String, List<Account>> groupByMonthOfBirth(List<Account> accounts);

    Double averageBalanceByCountry(List<Account> accounts, String country);

    String nameAndLastnameOfEmployees(List<List<Account>> accountsList);

    List<Account> sortByLastnameAndName(List<Account> accounts);

    Account oldestAccount(List<Account> accounts);

    Map<Integer, Double> groupByBirthYearAndAverageBalance(List<Account> accounts);

    Account longestLastname(List<Account> accounts);

}
