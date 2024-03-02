package ua.ithillel.app.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PaymentDTO {
    private Long id;
    @PositiveOrZero(message = "should be positive number or zero")
    private Double amount;
    @NotNull(message = "should not be null")
    @Positive(message = "should be positive number")
    private Long accountId;
}
