package ua.ithillel.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get Account by account id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account successfully founded",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDTO.class)) }),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> getAccountById(
            @Parameter(description = "account id")
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @Operation(summary = "Get all User Accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts successfully founded",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDTO.class)) }),
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<List<AccountDTO>> getAllAccountsByUserId(
            @Parameter(description = "user id")
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(accountService.getAllAccountsByUserId(id));
    }

    @Operation(summary = "Add Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Accounts successfully created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDTO.class)) }),
    })
    @PostMapping()
    public ResponseEntity<AccountDTO> addAccount(@Valid @RequestBody AccountDTO accountDTO) {
        return new ResponseEntity<>(accountService.addAccount(accountDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Editing Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts successfully changed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDTO.class)) }),
    })
    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> editAccount(
            @Parameter(description = "id of account to be edited")
            @PathVariable("id") Long id,
            @Valid @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.editAccount(id, accountDTO));
    }

    @Operation(summary = "Deleting Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts successfully deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDTO.class)) }),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<AccountDTO> deleteAccount(
            @Parameter(description = "id ff account to be deleted")
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(accountService.deleteAccount(id));
    }
}
