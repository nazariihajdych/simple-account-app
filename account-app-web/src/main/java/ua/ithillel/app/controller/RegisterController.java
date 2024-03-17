package ua.ithillel.app.controller;

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

    @PostMapping
    public ResponseEntity<UserDTO> register(@RequestBody LoginRegisterDTO loginRegisterDTO) throws InconsistencyException {
        UserDTO userDTO = authService.register(loginRegisterDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }
}
