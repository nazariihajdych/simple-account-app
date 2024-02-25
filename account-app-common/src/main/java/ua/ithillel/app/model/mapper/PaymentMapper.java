package ua.ithillel.app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ua.ithillel.app.model.Payment;
import ua.ithillel.app.model.dto.PaymentDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PaymentMapper {

    public abstract PaymentDTO paymentToPaymentDTO(Payment payment);

    public abstract Payment paymentDTOToPayment(PaymentDTO paymentDTO);
}
