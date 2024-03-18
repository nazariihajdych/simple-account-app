package ua.ithillel.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.ithillel.app.exception.GlobalExceptionHandler;
import ua.ithillel.app.model.dto.ErrorDTO;
import ua.ithillel.app.model.dto.LoginRegisterDTO;
import ua.ithillel.app.model.dto.UserDTO;
import ua.ithillel.app.service.AuthService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RegisterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    ObjectMapper objectMapper;
    LoginRegisterDTO loginUser;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new RegisterController(authService))
                .setControllerAdvice(new GlobalExceptionHandler()).build();
        loginUser = new LoginRegisterDTO();
    }

    @Test
    void registerTest_successfulRegistration() throws Exception {
        loginUser.setEmail("jonedou@mail.com");
        loginUser.setPassword("jonPassword");

        String jLoginUser = objectMapper.writeValueAsString(loginUser);

        mockMvc.perform(post("/register")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jLoginUser))
                .andExpect(status().isCreated())
                .andExpect(result -> assertInstanceOf(UserDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class)))
                .andExpect(jsonPath("$.email").value("jonedou@mail.com"))
                .andReturn();
    }

    @Test
    void registerTest_userExist() throws Exception {
        loginUser.setEmail("otheruser@mail.com");
        loginUser.setPassword("password");

        String jLoginUser = objectMapper.writeValueAsString(loginUser);

        mockMvc.perform(post("/register")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jLoginUser))
                .andExpect(result -> assertInstanceOf(ErrorDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDTO.class)))
                .andExpect(jsonPath("$.message").value("User with username 'otheruser@mail.com' already exists"))
                .andReturn();
    }
}