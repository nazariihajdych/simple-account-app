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
import ua.ithillel.app.model.dto.PaymentDTO;
import ua.ithillel.app.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Add new Payment to Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment successfully added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDTO.class)) }),
    })
    @PostMapping
    public ResponseEntity<PaymentDTO> addPayment(
            @Valid @RequestBody PaymentDTO paymentDTO) {
        return new ResponseEntity<>(paymentService.addPayment(paymentDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get Payment by payment id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment successfully founded",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDTO.class)) }),
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(
            @Parameter(description = "payment id")
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @Operation(summary = "Find all Payments of Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments successfully founded",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDTO.class)) }),
    })
    @GetMapping("/account")
    public ResponseEntity<List<PaymentDTO>> getAllPaymentsByAccountId(
            @Parameter(description = "accountId to find all payments")
            @RequestParam(name = "accountId") Long accountId) {
        return ResponseEntity.ok(paymentService.getAllPaymentsByAccountId(accountId));
    }

    @Operation(summary = "Find all Payments of User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments successfully founded",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDTO.class)) }),
    })
    @GetMapping("/user")
    public ResponseEntity<List<PaymentDTO>> getAllPaymentsByUserId(
            @Parameter(description = "userId to find all payments")
            @RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(paymentService.getAllPaymentsByUserId(userId));
    }

    @Operation(summary = "Editing Payments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments successfully changed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDTO.class)) }),
    })
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> editPayment(
            @Parameter(description = "id of payment to be changed")
            @PathVariable("id") Long id,
            @Valid @RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.ok(paymentService.editPayment(id, paymentDTO));
    }

    @Operation(summary = "Deleting Payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments successfully deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDTO.class)) }),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDTO> deletePayment(
            @Parameter(description = "id of payment to be deleted")
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(paymentService.deletePayment(id));
    }
}
