package ua.ithillel.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.ithillel.app.exception.GlobalExceptionHandler;
import ua.ithillel.app.model.dto.AuthDTO;
import ua.ithillel.app.model.dto.LoginRegisterDTO;
import ua.ithillel.app.model.dto.UserDTO;
import ua.ithillel.app.service.AuthService;
import ua.ithillel.app.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AuthService authService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService), new LoginController(authService))
                .setControllerAdvice(new GlobalExceptionHandler()).build();

        //maybe do auth here and give just token
    }

    @Test
    @Order(1)
    void addUser_successful() throws Exception {
        String email = "otheruser@mail.com";
        String password = "password1";

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("newUser@mail.com");
        userDTO.setPassword(passwordEncoder.encode("newPassword"));

        String jUserDTO = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/user")
                        .header("Authorization", "Bearer " + getAuthorization(email, password))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jUserDTO))
                .andExpect(status().isCreated())
                .andExpect(result -> assertInstanceOf(UserDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class)
                ))
                .andExpect(jsonPath("$.email").value("newUser@mail.com"))
                .andReturn();
    }

    @Test
    @Order(2)
    void getAllUsers_successful() throws Exception {
        String email = "otheruser@mail.com";
        String password = "password1";

        mockMvc.perform(get("/user")
                        .header("Authorization", "Bearer " + getAuthorization(email, password)))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(List.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), List.class)
                ))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].email").value("otheruser@mail.com"))
                .andReturn();
    }

    @Test
    @Order(3)
    void getUserById_successful() throws Exception {
        String email = "otheruser@mail.com";
        String password = "password1";

        mockMvc.perform(get("/user/{id}", 2L)
                        .header("Authorization", "Bearer " + getAuthorization(email, password)))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(UserDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class)
                ))
                .andExpect(jsonPath("$.email").value("nazar@mail.com"))
                .andReturn();
    }

    @Test
    @Order(4)
    void editUser_successful() throws Exception {
        String email = "otheruser@mail.com";
        String password = "password1";

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("updateUser@mail.com");
        String jUpdateUserDTO = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(put("/user/{id}", 2L)
                        .header("Authorization", "Bearer " + getAuthorization(email, password))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jUpdateUserDTO))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(UserDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class)
                ))
                .andExpect(jsonPath("$.email").value("updateUser@mail.com"))
                .andReturn();
    }

    @Test
    @Order(7)
    void removeUser_successful() throws Exception {
        String email = "otheruser@mail.com";
        String password = "password1";

        mockMvc.perform(delete("/user/{id}", 2L)
                        .header("Authorization", "Bearer " + getAuthorization(email, password)))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(UserDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class)
                ))
                .andExpect(jsonPath("$.email").value("updateUser@mail.com"))
                .andReturn();
    }

    @Test
    @Order(5)
    void setRole_successful() throws Exception {
        String email = "otheruser@mail.com";
        String password = "password1";

        mockMvc.perform(put("/user/role")
                        .param("userId", "2")
                        .param("roleId","2")
                        .header("Authorization", "Bearer " + getAuthorization(email, password)))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(UserDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class)
                ))
                .andDo(print())
                .andExpect(jsonPath("$.roles[0].roleName").value("ADMIN"))
                .andReturn();
    }

    @Test
    @Order(6)
    void removeRole_successful() throws Exception {
        String email = "otheruser@mail.com";
        String password = "password1";

        mockMvc.perform(delete("/user/role")
                        .param("userId", "2")
                        .param("roleId","2")
                        .header("Authorization", "Bearer " + getAuthorization(email, password)))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(UserDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class)
                ))
                .andDo(print())
                .andExpect(jsonPath("$.roles").isEmpty())
                .andReturn();
    }

    private String getAuthorization(String email, String password) throws Exception {
        LoginRegisterDTO loginUser = new LoginRegisterDTO();
        loginUser.setEmail(email);
        loginUser.setPassword(password);

        String jLoginUser = objectMapper.writeValueAsString(loginUser);

        MvcResult mvcResult = mockMvc.perform(post("/login")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jLoginUser))
                .andExpect(status().isOk())
                .andReturn();

        AuthDTO response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AuthDTO.class);
        return response.getToken();
    }
}