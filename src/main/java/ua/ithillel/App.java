package ua.ithillel;

import ua.ithillel.homework1.model.Account;
import ua.ithillel.homework2.service.AccountService;
import ua.ithillel.homework2.service.AccountServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class App {
    public static void main(String[] args) {
        Account jackAccount = new Account("Jack", "Boldom", "Canada", LocalDate.of(1997,3,10), 12002.6,"M");
        Account saraAccount = new Account("Sara", "Emmanek", "USA", LocalDate.of(1976,4,7), 5502.5,"W");
        Account vasilAccount = new Account("Vasil", "Horobyn", "Ukraine", LocalDate.of(1997,5,6), 13002.4,"M");
        Account maxymAccount = new Account("Maxym", "Sereda", "Ukraine", LocalDate.of(1988,5,6), 4002.244,"M");
        Account annaAccount = new Account("Anna", "Temna", "Poland", LocalDate.of(1955,3,5), 23002.234,"W");
        Account laurentiaAccount = new Account("Laurentia", "Vademach", "Canada", LocalDate.of(1989,4,2), 8600.5,"W");
        Account ilariaAccount = new Account("Ilaria", "Leymar", "Belgium", LocalDate.of(1966,2,23), 4567.6,"W");
        Account johnAccount = new Account("John", "Dou", "USA", LocalDate.of(1999,2,24), 983.8,"M");


        List<Account> accounts = List.of(jackAccount, saraAccount, vasilAccount, maxymAccount, annaAccount, laurentiaAccount, ilariaAccount, johnAccount);

        AccountService accountService = new AccountServiceImpl();

        Double balanceMoreThen = 12000.0;
        System.out.printf("Account balance more than %.2f:%n", balanceMoreThen);
        List<Account> accountsBalanceMoreThen = accountService.balanceMoreThen(accounts, balanceMoreThen);
        accountsBalanceMoreThen.forEach(System.out::println);

        System.out.println();
        System.out.println("Accounts countries: ");
        Set<String> accountsCountries = accountService.accountsCountries(accounts);
        System.out.println(accountsCountries);

        int youngerThenYear = 1998;
        boolean result = accountService.youngerThen(accounts, youngerThenYear);
        System.out.println();
        System.out.printf("Is there in accounts someone younger then %d : %b%n", youngerThenYear, result);

        System.out.println();
        Double sumForMaleBalance = accountService.sumOfMaleBalances(accounts);
        System.out.printf("Sum of male balances: %.2f%n", sumForMaleBalance);

        System.out.println();
        System.out.println("Accounts group by month of birth:");
        Map<String, List<Account>> groupedByMonthOfBirth = accountService.groupByMonthOfBirth(accounts);
        groupedByMonthOfBirth.forEach((key, value) -> System.out.println(key + " = " + value));

        String country = "USA";
        Double averageByCountry = accountService.averageBalanceByCountry(accounts, country);
        System.out.println();
        System.out.printf("Average balance by country: %s  - %.2f%n", country, averageByCountry);

        Account olehAccount = new Account("Oleh", "Soolinjuk", "Ukraine", LocalDate.of(1978,3,5), 242.234,"M");
        Account anastasiaAccount = new Account("Anastasia", "Meryn", "Belgium", LocalDate.of(1983,8,23), 3400.5,"W");
        Account lianaAccount = new Account("Liana", "Bakler", "Belgium", LocalDate.of(1996,11,21), 4467.6,"W");

        List<Account> otherAccounts = List.of(olehAccount, anastasiaAccount, lianaAccount);

        List<List<Account>> listOfAccountLists = List.of(accounts, otherAccounts);

        String nameAndLastnameOfEmployees = accountService.nameAndLastnameOfEmployees(listOfAccountLists);
        System.out.println();
        System.out.println("All employees: " + nameAndLastnameOfEmployees);

        System.out.println();
        System.out.println("Sorted by Lastname then by Name: ");
        accountService.sortByLastnameAndName(accounts).forEach(System.out::println);

        System.out.println();
        System.out.println("Oldest account: ");
        System.out.println(accountService.oldestAccount(accounts));

        System.out.println();
        System.out.println("Grouped by birth year and balance:");
        Map<Integer, Double> birthYearAndAverageBalance = accountService.groupByBirthYearAndAverageBalance(accounts);
        birthYearAndAverageBalance.forEach((key, value) -> System.out.println(key + " " + value));

        System.out.println();
        System.out.println("Longest Lastname account: ");
        System.out.println(accountService.longestLastname(accounts));
    }
}
