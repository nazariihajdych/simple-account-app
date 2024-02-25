package ua.ithillel.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.ithillel.app.model.dto.UserDTO;
import ua.ithillel.app.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.addUser(userDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> editUser(@PathVariable Long id,
                                            @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.editUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/role")
    public void setRole(@RequestParam(name = "userId") Long userId,
                        @RequestParam(name = "roleId") Long roleId) {
        userService.setUserRole(userId, roleId);
    }

    @DeleteMapping("/role")
    public void removeRole(@RequestParam(name = "userId") Long userId,
                           @RequestParam(name = "roleId") Long roleId) {
        userService.removeUserRole(userId, roleId);
    }
}
