package ua.ithillel.app.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.ithillel.app.exception.InconsistencyException;
import ua.ithillel.app.exception.UserNotFoundException;
import ua.ithillel.app.model.Role;
import ua.ithillel.app.model.User;
import ua.ithillel.app.model.UserDetailsImpl;
import ua.ithillel.app.model.dto.AuthDTO;
import ua.ithillel.app.model.dto.LoginRegisterDTO;
import ua.ithillel.app.model.dto.UserDTO;
import ua.ithillel.app.model.mapper.UserMapper;
import ua.ithillel.app.repo.RoleRepo;
import ua.ithillel.app.repo.UserRepo;
import ua.ithillel.app.util.JwtUtil;

import java.util.ArrayList;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final RoleRepo roleRepo;


    public AuthServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, UserMapper userMapper, JwtUtil jwtUtil, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
        this.roleRepo = roleRepo;
    }

    @Override
    public AuthDTO authenticate(LoginRegisterDTO loginRegisterDTO) throws UserNotFoundException {
        User byEmail = userRepo.findUserByEmail(loginRegisterDTO.getEmail());
        if (byEmail == null) {
            throw new UserNotFoundException("User with username '"
                    + loginRegisterDTO.getEmail() + "' does not exist");
        }

        boolean matches = passwordEncoder.matches(loginRegisterDTO.getPassword(), byEmail.getPassword());
        if (matches) {
            UserDetailsImpl appUserDetails = new UserDetailsImpl(byEmail);
            String token = jwtUtil.generateToken(appUserDetails);

            AuthDTO authDTO = new AuthDTO();
            authDTO.setUserDTO(userMapper.userToUserDTO(byEmail));
            authDTO.setToken(token);

            return authDTO;
        }
        return null;
    }

    @Override
    public UserDTO register(LoginRegisterDTO loginRegisterDTO) throws InconsistencyException {
        User byEmail = userRepo.findUserByEmail(loginRegisterDTO.getEmail());
        if (byEmail != null) {
            throw new InconsistencyException(
                    "User with username '" + loginRegisterDTO.getEmail() + "' already exists");
        }

        User user = new User();
        user.setEmail(loginRegisterDTO.getEmail());
        user.setPassword(passwordEncoder.encode(loginRegisterDTO.getPassword()));
        user.setAccounts(new ArrayList<>());
        user.setRoles(new ArrayList<>());
        User saved = userRepo.save(user);
        Role role = roleRepo.findByRoleName("USER");
        saved.getRoles().add(role);

        return userMapper.userToUserDTO(userRepo.save(user));

    }
}
