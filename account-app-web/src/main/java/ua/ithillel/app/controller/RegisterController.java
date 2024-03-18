package ua.ithillel.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.ithillel.app.exception.InconsistencyException;
import ua.ithillel.app.model.dto.LoginRegisterDTO;
import ua.ithillel.app.model.dto.UserDTO;
import ua.ithillel.app.service.AuthService;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private final AuthService authService;

    public RegisterController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully registered in app",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
    })
    @PostMapping
    public ResponseEntity<UserDTO> register(
            @RequestBody LoginRegisterDTO loginRegisterDTO) throws InconsistencyException {
        UserDTO userDTO = authService.register(loginRegisterDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }
}
