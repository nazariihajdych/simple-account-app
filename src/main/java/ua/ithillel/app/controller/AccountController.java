package ua.ithillel.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.ithillel.app.model.Account;
import ua.ithillel.app.service.AccountService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping( value = "/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Integer id){
        if (accountService.getAccountById(id) == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping()
    public ResponseEntity<List<Account>> getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PostMapping()
    public ResponseEntity<Account> addAccount(@RequestBody Account account){
        return ResponseEntity.ok(accountService.addAccount(account));
    }

    @GetMapping( "/balance/{moreThen}")
    public ResponseEntity<List<Account>> balanceMoreThen(@PathVariable Double moreThen){
        if(accountService.balanceMoreThen(moreThen).isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(accountService.balanceMoreThen(moreThen));
    }

    @GetMapping("/countries")
    public ResponseEntity<Set<String>> accountsCountries(){
        return ResponseEntity.ok(accountService.accountsCountries());
    }

    @GetMapping("/countries/average")
    public ResponseEntity<Double> averageBalanceByCountries(@RequestParam String country){
        return ResponseEntity.ok(accountService.averageBalanceByCountry(country));
    }
}
