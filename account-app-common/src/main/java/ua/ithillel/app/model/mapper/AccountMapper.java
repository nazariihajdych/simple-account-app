package ua.ithillel.app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ua.ithillel.app.model.Account;
import ua.ithillel.app.model.dto.AccountDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class AccountMapper {
    @Mapping(source = "account.user.id", target = "user_id")
    public abstract AccountDTO accountToAccountDTO(Account account);

    public abstract Account accountDTOToAccount(AccountDTO accountDTO);
}
