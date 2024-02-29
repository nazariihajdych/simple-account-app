package ua.ithillel.app.service;

import ua.ithillel.app.model.User;
import ua.ithillel.app.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO addUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO deleteUser(Long id);
    UserDTO editUser(Long id, UserDTO userDTO);

    UserDTO setUserRole(Long userId, Long roleId);

    UserDTO removeUserRole(Long userId, Long roleId);
}
