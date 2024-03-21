package ua.ithillel.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.ithillel.app.model.dto.AuthDTO;
import ua.ithillel.app.model.dto.LoginRegisterDTO;
import ua.ithillel.app.service.AuthService;
import ua.loggable.starter.aspect.Loggable;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @Loggable
    @Operation(summary = "Logging existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully logged in",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthDTO.class)) }),
    })
    @PostMapping
    public ResponseEntity<AuthDTO> login(
            @RequestBody LoginRegisterDTO loginRegisterDTO) throws UsernameNotFoundException {
        AuthDTO authenticate = authService.authenticate(loginRegisterDTO);
        return ResponseEntity.ok(authenticate);
    }
}
