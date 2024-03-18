package ua.ithillel.app.controller;

import jakarta.validation.Valid;
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
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<AccountDTO>> getAllAccountsByUserId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(accountService.getAllAccountsByUserId(id));
    }

    @PostMapping()
    public ResponseEntity<AccountDTO> addAccount(@Valid @RequestBody AccountDTO accountDTO) {
        return new ResponseEntity<>(accountService.addAccount(accountDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> editAccount(@PathVariable("id") Long id,
                                                  @Valid @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.editAccount(id, accountDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AccountDTO> deleteAccount(@PathVariable("id") Long id) {
        return ResponseEntity.ok(accountService.deleteAccount(id));
    }
}
