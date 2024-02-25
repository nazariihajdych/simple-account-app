package ua.ithillel.app.controller;

import org.springframework.web.bind.annotation.*;
import ua.ithillel.app.model.dto.RoleDTO;
import ua.ithillel.app.model.mapper.RolesMapper;
import ua.ithillel.app.repo.RoleRepo;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleRepo roleRepo;
    private final RolesMapper rolesMapper;

    public RoleController(RoleRepo roleRepo, RolesMapper rolesMapper) {
        this.roleRepo = roleRepo;
        this.rolesMapper = rolesMapper;
    }

    @PostMapping
    public void addRole(@RequestBody RoleDTO roleDTO) {
        roleRepo.save(rolesMapper.rolesDTOToRoles(roleDTO));
    }

    @DeleteMapping("/{id}")
    public void removeRole(@PathVariable Long id) {
        roleRepo.deleteById(id);
    }

}
