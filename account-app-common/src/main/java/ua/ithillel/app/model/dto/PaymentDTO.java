package ua.ithillel.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private Double amount;
    private Long account_id;
}
