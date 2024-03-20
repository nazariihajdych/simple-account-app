package ua.ithillel.app.service;

import org.springframework.stereotype.Service;
import ua.ithillel.app.exception.RoleNotFoundException;
import ua.ithillel.app.exception.UserNotFoundException;
import ua.ithillel.app.model.Role;
import ua.ithillel.app.model.User;
import ua.ithillel.app.model.dto.UserDTO;
import ua.ithillel.app.model.mapper.UserMapper;
import ua.ithillel.app.repo.RoleRepo;
import ua.ithillel.app.repo.UserRepo;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
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
    public UserDTO deleteUser(Long id) {
        User user = findUserOrThrow(id);
        user.setRoles(null);
        userRepo.save(user);
        userRepo.deleteById(id);

        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO editUser(Long id, UserDTO userDTO) {
        User user = findUserOrThrow(id);
        user.setEmail(userDTO.getEmail());
        User saved = userRepo.save(user);
        return userMapper.userToUserDTO(saved);
    }

    @Override
    public UserDTO setUserRole(Long userId, Long roleId) {
        User user = findUserOrThrow(userId);

        Role role = roleRepo.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role with id = " + roleId + " not found"));

        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            return userMapper.userToUserDTO(userRepo.save(user));
        }
        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO removeUserRole(Long userId, Long roleId) {
        User user = findUserOrThrow(userId);
        user.getRoles().removeIf(role -> role.getId().equals(roleId));
        return userMapper.userToUserDTO(userRepo.save(user));
    }

    private User findUserOrThrow(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + userId + " not found"));

    }
}
