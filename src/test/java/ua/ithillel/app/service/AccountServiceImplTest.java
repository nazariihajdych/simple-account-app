package ua.ithillel.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.ithillel.app.model.Account;
import ua.ithillel.app.repo.InMemoryRepo;
import ua.ithillel.app.repo.InMemoryRepoImpl;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AccountServiceImplTest {

//    private InMemoryRepo inMemoryRepo;
//    private AccountService accountService;
//    private List<Account> accounts;
//    private List<Account> accountsWomen;
//    @BeforeEach
//    void setUp() {
//        Account jackAccount = new Account("Jack", "Boldom", "Canada", 2000.0,"M");
//        Account saraAccount = new Account("Sara", "Emmanek", "USA", 5000.0,"W");
//        Account laurentiaAccount = new Account("Laurentia", "Vademach", "Canada", 3000.0,"W");
//        Account vasilAccount = new Account("Vasil", "Horobyn", "Ukraine", 1300.0,"M");
//
//        accounts = List.of(jackAccount, saraAccount, vasilAccount, laurentiaAccount);
//        accountsWomen = List.of(saraAccount, laurentiaAccount);
//        inMemoryRepo = new InMemoryRepoImpl();
//        accountService = new AccountServiceImpl(inMemoryRepo);
//        accounts.forEach(account -> accountService.addAccount(account));
//    }
//
//    @Test
//    void balanceMoreThenTest_success() {
//        List<Account> balanceMoreThen = accountService.balanceMoreThen(3000.0);
//
//        int expectedSizeValue = 1;
//        String expectedNameValue = "Sara";
//
//        assertNotNull(balanceMoreThen.get(0));
//        assertEquals(expectedNameValue, balanceMoreThen.get(0).getFirstName());
//        assertEquals(expectedSizeValue, balanceMoreThen.size());
//    }
//
//    @Test
//    void balanceMoreThenTest_noSuchElement() {
//        List<Account> balanceMoreThen = accountService.balanceMoreThen(23000.0);
//
//        int expectedSizeValue = 0;
//
//        assertEquals(expectedSizeValue, balanceMoreThen.size());
//    }
//
//    @Test
//    void accountsCountriesTest_success() {
//        Set<String> countries = accountService.accountsCountries();
//
//        int expectedSizeValue = 3;
//
//        assertEquals(expectedSizeValue, countries.size());
//        assertTrue(countries.contains("Ukraine"));
//        assertTrue(countries.contains("Canada"));
//        assertTrue(countries.contains("USA"));
//    }
//    @Test
//    void sumOfMaleBalancesTest_success() {
//        Double sumOfMaleBalances = accountService.sumOfMaleBalances(accounts);
//
//        Double expectedValue = 3300.0;
//
//        assertEquals(expectedValue, sumOfMaleBalances);
//    }
//
//    @Test
//    void sumOfMaleBalancesTest_noMale() {
//        Double sumOfMaleBalances = accountService.sumOfMaleBalances(accountsWomen);
//
//        Double expectedValue = 0.0;
//
//        assertEquals(expectedValue, sumOfMaleBalances);
//    }
//
//    @Test
//    void averageBalanceByCountryTest_success() {
//        Double averageBalanceByCountry = accountService.averageBalanceByCountry("Canada");
//
//        Double expectedValue = 2500.0;
//
//        assertEquals(expectedValue, averageBalanceByCountry);
//    }
//
//    @Test
//    void nameAndLastnameOfEmployeesTest_success() {
//        List<List<Account>> accountList = List.of(accounts);
//        String nameAndLastnameOfEmployees = accountService.nameAndLastnameOfEmployees(accountList);
//
//        String expectedValue = "Jack Boldom, Sara Emmanek, Vasil Horobyn, Laurentia Vademach";
//
//        assertEquals(expectedValue, nameAndLastnameOfEmployees);
//    }
//
//    @Test
//    void sortByLastnameAndNameTest_success() {
//        List<Account> sortByLastnameAndName = accountService.sortByLastnameAndName(accounts);
//
//        assertNotEquals(0, sortByLastnameAndName.size());
//        assertEquals("Jack", sortByLastnameAndName.get(0).getFirstName());
//        assertEquals("Sara", sortByLastnameAndName.get(1).getFirstName());
//    }
//
//    @Test
//    void longestLastnameTest_success() {
//        Account longestLastname = accountService.longestLastname(accounts);
//
//        String expectedValue = "Vademach";
//
//        assertNotNull(longestLastname);
//        assertEquals(expectedValue, longestLastname.getLastName());
//
//    }

}