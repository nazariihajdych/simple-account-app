package ua.ithillel.app.service;

import org.springframework.stereotype.Service;
import ua.ithillel.app.exeption.RoleNotFoundException;
import ua.ithillel.app.exeption.UserNotFoundException;
import ua.ithillel.app.model.Role;
import ua.ithillel.app.model.User;
import ua.ithillel.app.model.dto.UserDTO;
import ua.ithillel.app.model.mapper.RolesMapper;
import ua.ithillel.app.model.mapper.UserMapper;
import ua.ithillel.app.repo.RoleRepo;
import ua.ithillel.app.repo.UserRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final UserMapper userMapper;
    private final RolesMapper rolesMapper;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, UserMapper userMapper, RolesMapper rolesMapper) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.userMapper = userMapper;
        this.rolesMapper = rolesMapper;
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
//        if (userDTO.getAccounts() == null) userDTO.setAccounts(new ArrayList<>());
//        if (userDTO.getRoles() == null) userDTO.setRoles(new ArrayList<>());
        User user = userMapper.userDTOToUser(userDTO);
        User saved = userRepo.save(user);
        return userMapper.userToUserDTO(saved);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> userRepoAll = userRepo.findAll();
        return userRepoAll.stream()
                .map(userMapper::userToUserDTO)
                .toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = findUserOrThrow(id);
        return userMapper.userToUserDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
        //fix delete
    }

    @Override
    public UserDTO editUser(Long id, UserDTO userDTO) {
        User user = findUserOrThrow(id);
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRoles(userDTO.getRoles()
                .stream()
                .map(rolesMapper::rolesDTOToRoles)
                .toList());
        User saved = userRepo.save(user);
        return userMapper.userToUserDTO(saved);
    }

    @Override
    public void setUserRole(Long userId, Long roleId) {
        User user = findUserOrThrow(userId);

        Role role = roleRepo.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role with id = " + roleId + "not found"));

        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            userRepo.save(user);
        }
    }

    @Override
    public void removeUserRole(Long userId, Long roleId) {
        User user = findUserOrThrow(userId);
        Role role = roleRepo.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role with id = " + roleId + "not found"));
        user.getRoles().remove(role);
        userRepo.save(user);
    }

    private User findUserOrThrow(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + userId + "not found"));

    }
}
