package ua.ithillel.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.ithillel.app.exception.GlobalExceptionHandler;
import ua.ithillel.app.exception.UserNotFoundException;
import ua.ithillel.app.model.dto.AuthDTO;
import ua.ithillel.app.model.dto.ErrorDTO;
import ua.ithillel.app.model.dto.LoginRegisterDTO;
import ua.ithillel.app.service.AuthService;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    ObjectMapper objectMapper;
    LoginRegisterDTO loginUser;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(authService))
                .setControllerAdvice(new GlobalExceptionHandler()).build();
        loginUser = new LoginRegisterDTO();
    }

    @Test
    void loginTest_Successful_Login() throws Exception {
        loginUser.setEmail("otheruser@mail.com");
        loginUser.setPassword("password1");

        String jLoginUser = objectMapper.writeValueAsString(loginUser);

        mockMvc.perform(post("/login")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jLoginUser))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(AuthDTO.class,
                                objectMapper.readValue(result.getResponse().getContentAsString(), AuthDTO.class))

                )
                .andExpect(jsonPath("$.userDTO.email").value("otheruser@mail.com"))
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(result -> assertEquals(275,
                        objectMapper.readValue(result.getResponse().getContentAsString(), AuthDTO.class).getToken().length()))
                .andReturn();
    }

    @Test
    void loginTest_WrongPassword() throws Exception {
        loginUser.setEmail("otheruser@mail.com");
        loginUser.setPassword("password2");

        String jLoginUser = objectMapper.writeValueAsString(loginUser);

        mockMvc.perform(post("/login")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jLoginUser))
                .andExpect(result -> assertInstanceOf(ErrorDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDTO.class)))
                .andExpect(jsonPath("$.message").value("Wrong password!"))
                .andReturn();
    }

    @Test
    void loginTest_Throw_UserNotFoundException() throws Exception {
        loginUser.setEmail("otheruser2@mail.com");
        loginUser.setPassword("password1");

        String jLoginUser = objectMapper.writeValueAsString(loginUser);

        mockMvc.perform(post("/login")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jLoginUser))
                .andExpect(result -> assertInstanceOf(UserNotFoundException.class, result.getResolvedException()))
                .andReturn();
    }
}