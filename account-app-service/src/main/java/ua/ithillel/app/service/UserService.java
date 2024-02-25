package ua.ithillel.app.service;

import ua.ithillel.app.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO addUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    void deleteUser(Long id);
    UserDTO editUser(Long id, UserDTO userDTO);

    void setUserRole(Long userId, Long roleId);

    void removeUserRole(Long userId, Long roleId);
}
