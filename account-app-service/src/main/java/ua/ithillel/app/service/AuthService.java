package ua.ithillel.app.service;

import ua.ithillel.app.exception.InconsistencyException;
import ua.ithillel.app.exception.UserNotFoundException;
import ua.ithillel.app.model.dto.AuthDTO;
import ua.ithillel.app.model.dto.LoginRegisterDTO;
import ua.ithillel.app.model.dto.UserDTO;

public interface AuthService {
    AuthDTO authenticate(LoginRegisterDTO loginRegisterDTO) throws UserNotFoundException;

    UserDTO register(LoginRegisterDTO loginRegisterDTO) throws InconsistencyException;
}
