package ua.ithillel.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.ithillel.app.model.dto.AccountDTO;
import ua.ithillel.app.service.AccountService;

import java.util.List;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<AccountDTO>> getAllAccountsByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAllAccountsByUserId(id));
    }

    @PostMapping()
    public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO accountDTO) {
        return new ResponseEntity<>(accountService.addAccount(accountDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> editAccount(@PathVariable Long id,
                                               @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.editAccount(id, accountDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AccountDTO> deleteAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.deleteAccount(id));
    }
}
