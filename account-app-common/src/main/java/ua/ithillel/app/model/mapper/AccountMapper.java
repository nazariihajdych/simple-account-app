package ua.ithillel.app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ua.ithillel.app.model.Account;
import ua.ithillel.app.model.Payment;
import ua.ithillel.app.model.dto.AccountDTO;
import ua.ithillel.app.model.dto.PaymentDTO;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = PaymentMapper.class)
public abstract class AccountMapper {
    @Autowired
    protected PaymentMapper paymentMapper;
    @Mapping(source = "account.user.id", target = "user_id")
    @Mapping(source = "payments", target = "payments", qualifiedByName = "paymentToPaymentDTO")
    public abstract AccountDTO accountToAccountDTO(Account account);

    public abstract Account accountDTOToAccount(AccountDTO accountDTO);

    @Named("paymentToPaymentDTO")
    public List<PaymentDTO> paymentToPaymentDTO(List<Payment> payments) {
        if (payments == null || payments.isEmpty()) {
            return new ArrayList<>();
        }

        return payments.stream()
                .map(paymentMapper::paymentToPaymentDTO)
                .toList();
    }
}
