package ua.ithillel.app.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public ResponseEntity<PaymentDTO> addPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        return new ResponseEntity<>(paymentService.addPayment(paymentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping("/account")
    public ResponseEntity<List<PaymentDTO>> getAllPaymentsByAccountId(@RequestParam(name = "accountId") Long accountId) {
        return ResponseEntity.ok(paymentService.getAllPaymentsByAccountId(accountId));
    }

    @GetMapping("/user")
    public ResponseEntity<List<PaymentDTO>> getAllPaymentsByUserId(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(paymentService.getAllPaymentsByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> editPayment(@PathVariable Long id,
                                                  @Valid @RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.ok(paymentService.editPayment(id, paymentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDTO> deletePayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.deletePayment(id));
    }
}
