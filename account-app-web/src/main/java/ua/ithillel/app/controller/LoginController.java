package ua.ithillel.app.controller;

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
    @PostMapping
    public ResponseEntity<AuthDTO> login(@RequestBody LoginRegisterDTO loginRegisterDTO) throws UsernameNotFoundException {
        AuthDTO authenticate = authService.authenticate(loginRegisterDTO);
        return ResponseEntity.ok(authenticate);
    }
}
