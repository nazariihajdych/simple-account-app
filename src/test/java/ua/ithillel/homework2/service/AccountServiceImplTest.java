package ua.ithillel.homework2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.ithillel.homework1.model.Account;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AccountServiceImplTest {

    private AccountService accountService;
    private List<Account> accounts;
    private List<Account> accountsWomen;
    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl();

        Account jackAccount = new Account("Jack", "Boldom", "Canada", LocalDate.of(1997,3,10), 2000.0,"M");
        Account saraAccount = new Account("Sara", "Emmanek", "USA", LocalDate.of(1976,4,7), 5000.0,"W");
        Account laurentiaAccount = new Account("Laurentia", "Vademach", "Canada", LocalDate.of(1989,4,2), 3000.0,"W");
        Account vasilAccount = new Account("Vasil", "Horobyn", "Ukraine", LocalDate.of(1997,5,6), 1300.0,"M");

        accounts = List.of(jackAccount, saraAccount, vasilAccount, laurentiaAccount);
        accountsWomen = List.of(saraAccount, laurentiaAccount);
    }

    @Test
    void balanceMoreThenTest_success() {
        List<Account> balanceMoreThen = accountService.balanceMoreThen(accounts, 3000.0);

        int expectedSizeValue = 1;
        String expectedNameValue = "Sara";

        assertNotNull(balanceMoreThen.get(0));
        assertEquals(expectedNameValue, balanceMoreThen.get(0).getFirstName());
        assertEquals(expectedSizeValue, balanceMoreThen.size());
    }

    @Test
    void balanceMoreThenTest_noSuchElement() {
        List<Account> balanceMoreThen = accountService.balanceMoreThen(accounts, 23000.0);

        int expectedSizeValue = 0;

        assertEquals(expectedSizeValue, balanceMoreThen.size());
    }

    @Test
    void accountsCountriesTest_success() {
        Set<String> countries = accountService.accountsCountries(accounts);

        int expectedSizeValue = 3;

        assertEquals(expectedSizeValue, countries.size());
        assertTrue(countries.contains("Ukraine"));
        assertTrue(countries.contains("Canada"));
        assertTrue(countries.contains("USA"));
    }

    @Test
    void youngerThenTest_success() {
        assertTrue(accountService.youngerThen(accounts, 1976));
        assertFalse(accountService.youngerThen(accounts, 1997));
    }

    @Test
    void sumOfMaleBalancesTest_success() {
        Double sumOfMaleBalances = accountService.sumOfMaleBalances(accounts);

        Double expectedValue = 3300.0;

        assertEquals(expectedValue, sumOfMaleBalances);
    }

    @Test
    void sumOfMaleBalancesTest_noMale() {
        Double sumOfMaleBalances = accountService.sumOfMaleBalances(accountsWomen);

        Double expectedValue = 0.0;

        assertEquals(expectedValue, sumOfMaleBalances);
    }

    @Test
    void groupByMonthOfBirthTest_success() {
        Map<String, List<Account>> groupedByMonthOfBirth = accountService.groupByMonthOfBirth(accounts);

        int expectedSizeValue = 3;

        assertEquals(expectedSizeValue, groupedByMonthOfBirth.size());
    }

    @Test
    void averageBalanceByCountryTest_success() {
        Double averageBalanceByCountry = accountService.averageBalanceByCountry(accounts, "Canada");

        Double expectedValue = 2500.0;

        assertEquals(expectedValue, averageBalanceByCountry);
    }

    @Test
    void nameAndLastnameOfEmployeesTest_success() {
        List<List<Account>> accountList = List.of(accounts);
        String nameAndLastnameOfEmployees = accountService.nameAndLastnameOfEmployees(accountList);

        String expectedValue = "Jack Boldom, Sara Emmanek, Vasil Horobyn, Laurentia Vademach";

        assertEquals(expectedValue, nameAndLastnameOfEmployees);
    }

    @Test
    void sortByLastnameAndNameTest_success() {
        List<Account> sortByLastnameAndName = accountService.sortByLastnameAndName(accounts);

        assertNotEquals(0, sortByLastnameAndName.size());
        assertEquals("Jack", sortByLastnameAndName.get(0).getFirstName());
        assertEquals("Sara", sortByLastnameAndName.get(1).getFirstName());
    }

    @Test
    void oldestAccountTest_success() {
        Account oldestAccount = accountService.oldestAccount(accounts);

        String expectedValue = "Sara";

        assertEquals(expectedValue, oldestAccount.getFirstName());
    }

    @Test
    void groupByBirthYearAndAverageBalanceTest_success() {
        Map<Integer, Double> birthYearAndAverageBalance = accountService.groupByBirthYearAndAverageBalance(accounts);

        int expectedSizeValue = 3;
        int groupByYear = 1997;
        Double expectedAverageBalance = 1650.0;

        assertEquals(expectedSizeValue, birthYearAndAverageBalance.size());
        assertEquals(expectedAverageBalance, birthYearAndAverageBalance.get(groupByYear));
    }

    @Test
    void longestLastnameTest_success() {
        Account longestLastname = accountService.longestLastname(accounts);

        String expectedValue = "Vademach";

        assertNotNull(longestLastname);
        assertEquals(expectedValue, longestLastname.getLastName());

    }

}