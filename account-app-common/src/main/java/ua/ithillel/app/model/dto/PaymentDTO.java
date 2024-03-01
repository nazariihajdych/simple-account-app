package ua.ithillel.app.model.dto;

import lombok.Data;

@Data
public class PaymentDTO {
    private Long id;
    private Double amount;
    private Long accountId;
}
