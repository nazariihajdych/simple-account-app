package ua.ithillel.app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ua.ithillel.app.model.Role;
import ua.ithillel.app.model.User;
import ua.ithillel.app.model.dto.RoleDTO;
import ua.ithillel.app.model.dto.UserDTO;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {RolesMapper.class})
public abstract class UserMapper {
//    @Autowired
//    protected RolesMapper rolesMapper;
//    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToRolesDTO")
    public abstract UserDTO userToUserDTO(User user);

    public abstract User userDTOToUser(UserDTO userDTO);

//    @Named("rolesToRolesDTO")
//    public List<RoleDTO> rolesToRolesDTO(List<Role> roles) {
//        return roles.stream()
//                .map(rolesMapper::rolesToRolesDTO)
//                .toList();
//    }
}
