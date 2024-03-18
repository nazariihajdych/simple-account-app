package ua.ithillel.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

    @Operation(summary = "Add User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
    })
    @PostMapping
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.addUser(userDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get User by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User founded",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(description = "id of user to be searched")
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Get all Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users founded",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Editing User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully changed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> editUser(
            @Parameter(description = "id of user to be edit")
            @PathVariable("id") Long id,
            @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.editUser(id, userDTO));
    }

    @Operation(summary = "Deleting User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> removeUser(
            @Parameter(description = "id of user to be deleted")
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @Operation(summary = "Set Role for User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role successfully added to User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
    })
    @PutMapping("/role")
    public ResponseEntity<UserDTO> setRole(
            @Parameter(description = "id of user to add role")
            @RequestParam(name = "userId") Long userId,
            @Parameter(description = "id of role to be add to user")
            @RequestParam(name = "roleId") Long roleId) {
        return ResponseEntity.ok(userService.setUserRole(userId, roleId));
    }

    @Operation(summary = "Deleting Role for User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role successfully deleted to User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
    })
    @DeleteMapping("/role")
    public ResponseEntity<UserDTO> removeRole(
            @Parameter(description = "id of user to delete role")
            @RequestParam(name = "userId") Long userId,
            @Parameter(description = "id of role to delete from user")
            @RequestParam(name = "roleId") Long roleId) {
        return ResponseEntity.ok(userService.removeUserRole(userId, roleId));
    }
}
