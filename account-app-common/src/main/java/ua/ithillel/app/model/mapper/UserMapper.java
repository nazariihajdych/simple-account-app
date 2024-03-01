package ua.ithillel.app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ua.ithillel.app.model.Account;
import ua.ithillel.app.model.Role;
import ua.ithillel.app.model.User;
import ua.ithillel.app.model.dto.AccountDTO;
import ua.ithillel.app.model.dto.RoleDTO;
import ua.ithillel.app.model.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {RolesMapper.class, AccountMapper.class})
public abstract class UserMapper {
    @Autowired
    protected RolesMapper rolesMapper;
    @Autowired
    protected AccountMapper accountMapper;

    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToRolesDTO")
    @Mapping(source = "accounts", target = "accounts", qualifiedByName = "accountToAccountDTO")
    public abstract UserDTO userToUserDTO(User user);

    public abstract User userDTOToUser(UserDTO userDTO);

    @Named("rolesToRolesDTO")
    public List<RoleDTO> rolesToRolesDTO(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return new ArrayList<>();
        }

        return roles.stream()
                .map(rolesMapper::rolesToRolesDTO)
                .toList();
    }

    @Named("accountToAccountDTO")
    public List<AccountDTO> accountToAccountDTO(List<Account> accounts) {
        if (accounts == null || accounts.isEmpty()) {
            return new ArrayList<>();
        }

        return accounts.stream()
                .map(accountMapper::accountToAccountDTO)
                .toList();
    }

}
