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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ua.ithillel.app.exception.AccountNotFoundException;
import ua.ithillel.app.exception.GlobalExceptionHandler;
import ua.ithillel.app.model.dto.AccountDTO;
import ua.ithillel.app.model.dto.AuthDTO;
import ua.ithillel.app.model.dto.LoginRegisterDTO;
import ua.ithillel.app.model.enums.Gender;
import ua.ithillel.app.service.AccountService;
import ua.ithillel.app.service.AuthService;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountControllerTest {
    MockMvc mockMvc;
    String token;

    @Autowired
    AccountService accountService;

    @Autowired
    AuthService authService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AccountController(accountService), new LoginController(authService))
                .setControllerAdvice(new GlobalExceptionHandler()).build();

        String email = "otheruser@mail.com";
        String password = "password1";

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
        this.token = response.getToken();
    }

    @Test
    @Order(1)
    void addAccount_successful() throws Exception {

        AccountDTO fakeAccountDTO = new AccountDTO();
        fakeAccountDTO.setFirstName("Vasil");
        fakeAccountDTO.setLastName("Variman");
        fakeAccountDTO.setDateOfBirth(new SimpleDateFormat("ddMMyyyy").parse("01011999"));
        fakeAccountDTO.setCountry("Ukraine");
        fakeAccountDTO.setBalance((double) 200);
        fakeAccountDTO.setGender(Gender.MALE);
        fakeAccountDTO.setUser_id(1L);

        String jFakeAccount = objectMapper.writeValueAsString(fakeAccountDTO);

        mockMvc.perform(post("/account")
                        .header("Authorization", "Bearer " + token)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jFakeAccount))
                .andExpect(status().isCreated())
                .andExpect(result -> assertInstanceOf(AccountDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), AccountDTO.class)
                ))
                .andExpect(jsonPath("$.firstName").value("Vasil"))
                .andExpect(jsonPath("$.balance").value(200.0))
                .andReturn();
    }

    @Test
    @Order(2)
    void addAccount_unsuccessful_passingNotValidFieldValues() throws Exception {

        HashMap<String, String> expectedValues = new HashMap<>();
        expectedValues.put("firstName", "should be more then one character");
        expectedValues.put("lastName", "should be more then one character");
        expectedValues.put("balance", "should be positive number or zero");
        expectedValues.put("dateOfBirth", "should be in past");
        expectedValues.put("user_id", "should be positive number");


        AccountDTO fakeAccountDTO = new AccountDTO();
        fakeAccountDTO.setFirstName("");
        fakeAccountDTO.setLastName("");
        fakeAccountDTO.setDateOfBirth(new SimpleDateFormat("ddMMyyyy").parse("01012028"));
        fakeAccountDTO.setCountry("Ukraine");
        fakeAccountDTO.setBalance((double) -10);
        fakeAccountDTO.setGender(Gender.MALE);
        fakeAccountDTO.setUser_id(0L);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonAccountDTO = objectMapper.writeValueAsString(fakeAccountDTO);

        mockMvc.perform(post("/account")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAccountDTO))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals(expectedValues,
                        objectMapper.readValue(result.getResponse().getContentAsString(), HashMap.class)))
                .andReturn();
    }

    @Test
    @Order(3)
    void getAccountById_successful() throws Exception {
        mockMvc.perform(get("/account/{id}", 1L)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(AccountDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), AccountDTO.class)
                ))
                .andExpect(jsonPath("$.firstName").value("Nazar"))
                .andExpect(jsonPath("$.user_id").value(1))
                .andReturn();
    }

    @Test
    @Order(4)
        //how its work without token in header
    void getAccountById_unsuccessful_NotExistingAccount() throws Exception {
        mockMvc.perform(get("/account/999"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(AccountNotFoundException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Account with id = 999 not found",
                        result.getResolvedException().getMessage()))
                .andReturn();
    }

    @Test
    @Order(5)
    void getAllAccountsByUserId_successful() throws Exception {
        mockMvc.perform(get("/account/user/{id}", 1L)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(List.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), List.class)
                ))
                .andExpect(jsonPath("$.[0].firstName").value("Nazar"))
                .andReturn();
    }

    @Test
    @Order(6)
    void editAccount_successful() throws Exception {

        AccountDTO fakeAccountDTO = new AccountDTO();
        fakeAccountDTO.setFirstName("Changed");
        fakeAccountDTO.setLastName("Account");
        fakeAccountDTO.setDateOfBirth(new SimpleDateFormat("ddMMyyyy").parse("01011999"));
        fakeAccountDTO.setCountry("Ukraine");
        fakeAccountDTO.setBalance((double) 200);
        fakeAccountDTO.setGender(Gender.MALE);
        fakeAccountDTO.setUser_id(1L);

        String jFakeAccountDTO = objectMapper.writeValueAsString(fakeAccountDTO);

        mockMvc.perform(put("/account/{id}", 1L)
                        .header("Authorization", "Bearer " + token)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jFakeAccountDTO))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(AccountDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), AccountDTO.class)
                ))
                .andExpect(jsonPath("$.firstName").value("Changed"))
                .andReturn();
    }

    @Test
    @Order(7)
    void deleteAccount_successful() throws Exception {

        mockMvc.perform(delete("/account/{id}", 1L)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(AccountDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), AccountDTO.class)
                ))
                .andExpect(jsonPath("$.firstName").value("Changed"))
                .andReturn();
    }
}