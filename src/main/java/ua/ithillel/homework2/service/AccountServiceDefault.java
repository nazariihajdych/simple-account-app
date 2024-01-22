package ua.ithillel.homework2.service;

import ua.ithillel.homework1.model.Account;

import java.util.*;
import java.util.stream.Collectors;

public class AccountServiceDefault implements AccountService {

    @Override
    public List<Account> balanceMoreThen(List<Account> accounts, Double moreThen) {
        return accounts.stream()
                .filter(account -> account.getBalance() > moreThen)
                .toList();
    }

    @Override
    public Set<String> accountsCountries(List<Account> accounts) {
        return accounts.stream()
                .map(Account::getCountry)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean youngerThen(List<Account> accounts, int youngerThenYear) {
        return accounts.stream()
                .anyMatch(account -> account.getBirthday().getYear() > youngerThenYear);
    }

    @Override
    public Double sumOfMaleBalances(List<Account> accounts) {
        return accounts.stream()
                .filter(account -> account.getGender().equals("M"))
                .mapToDouble(Account::getBalance)
                .sum();
    }

    @Override
    public Map<String, List<Account>> groupByMonthOfBirth(List<Account> accounts) {
        return accounts.stream()
                .collect(Collectors.groupingBy(account -> account.getBirthday().getMonth().toString()));
    }

    @Override
    public Double averageBalanceDependOfCountry(List<Account> accounts, String country) {
        return accounts.stream()
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
    public Account oldestAccount(List<Account> accounts) {
        return accounts.stream()
                .min(Comparator.comparing(Account::getBirthday))
                .orElse(null);
    }

    @Override
    public Map<Integer, Double> groupByBirthYearAndAverageBalance(List<Account> accounts) {
        return accounts.stream()
                .collect(Collectors.groupingBy(account -> account.getBirthday().getYear(),
                        Collectors.averagingDouble(Account::getBalance)));
    }

    @Override
    public Account longestLastname(List<Account> accounts) {
        return accounts.stream()
                .max(Comparator.comparingInt(account -> account.getLastName().length()))
                .orElse(null);
    }
}
