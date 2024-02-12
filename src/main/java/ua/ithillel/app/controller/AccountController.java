package ua.ithillel.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.ithillel.app.model.Account;
import ua.ithillel.app.service.AccountService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account accountById = accountService.getAccountById(id);
        return ResponseEntity.ok(accountById);
    }

    @GetMapping()
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PostMapping()
    public ResponseEntity<Account> addAccount(@RequestBody Account account) {
        return new ResponseEntity<>(accountService.addAccount(account), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> editAccount(@PathVariable Long id,
                                               @RequestBody Account account) {
        return ResponseEntity.ok(accountService.editAccount(id, account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.deleteAccount(id));
    }

    @GetMapping("/balance/{moreThen}")
    public ResponseEntity<List<Account>> balanceMoreThen(@PathVariable Double moreThen) {
        List<Account> accountList = accountService.balanceMoreThen(moreThen);
        if (accountList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(accountList);
        }
    }

    @GetMapping("/countries")
    public ResponseEntity<Set<String>> accountsCountries() {
        return ResponseEntity.ok(accountService.accountsCountries());
    }

    @GetMapping("/countries/average")
    public ResponseEntity<Double> averageBalanceByCountries(@RequestParam String country) {
        return ResponseEntity.ok(accountService.averageBalanceByCountry(country));
    }
}
