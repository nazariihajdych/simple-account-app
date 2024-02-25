package ua.ithillel.app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ua.ithillel.app.model.Role;
import ua.ithillel.app.model.dto.RoleDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class RolesMapper {

    public abstract RoleDTO rolesToRolesDTO(Role role);
    public abstract Role rolesDTOToRoles(RoleDTO roleDTO);
}
