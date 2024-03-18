package ua.ithillel.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer;
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
import ua.ithillel.app.exception.GlobalExceptionHandler;
import ua.ithillel.app.exception.PaymentNotFoundException;
import ua.ithillel.app.model.dto.AuthDTO;
import ua.ithillel.app.model.dto.LoginRegisterDTO;
import ua.ithillel.app.model.dto.PaymentDTO;
import ua.ithillel.app.service.AuthService;
import ua.ithillel.app.service.PaymentService;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentControllerTest {
    MockMvc mockMvc;
    String token;

    @Autowired
    PaymentService paymentService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AuthService authService;

    Double dbPaymentAmount = 123.5;
    Long dbPaymentAccountId = 1L;

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new PaymentController(paymentService), new LoginController(authService))
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
    void addPayment_successful() throws Exception {

        PaymentDTO fakePaymentDTO = new PaymentDTO();
        fakePaymentDTO.setAmount(200.0);
        fakePaymentDTO.setAccountId(1L);

        String jFakePayment = objectMapper.writeValueAsString(fakePaymentDTO);

        mockMvc.perform(post("/payment")
                        .header("Authorization", "Bearer " + token)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jFakePayment))
                .andExpect(status().isCreated())
                .andExpect(result -> assertInstanceOf(PaymentDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), PaymentDTO.class)
                ))
                .andExpect(jsonPath("$.amount").value(200.0))
                .andReturn();
    }

    @Test
    @Order(2)
    void addPayment_unsuccessful_passingNotValidFieldValues() throws Exception {

        HashMap<String, String> expectedValues = new HashMap<>();
        expectedValues.put("amount", "should be positive number or zero");
        expectedValues.put("accountId", "should be positive number");

        PaymentDTO fakePaymentDTO = new PaymentDTO();
        fakePaymentDTO.setAmount(-200.0);
        fakePaymentDTO.setAccountId(0L);

        String jFakePayment = objectMapper.writeValueAsString(fakePaymentDTO);

        mockMvc.perform(post("/payment")
                        .header("Authorization", "Bearer " + token)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jFakePayment))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals(expectedValues,
                        objectMapper.readValue(result.getResponse().getContentAsString(), HashMap.class)))
                .andReturn();
    }

    @Test
    @Order(3)
    void getPaymentById_successful() throws Exception {
        mockMvc.perform(get("/payment/{id}", 1L)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(PaymentDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), PaymentDTO.class)
                ))
                .andExpect(jsonPath("$.amount").value(dbPaymentAmount))
                .andExpect(jsonPath("$.accountId").value(dbPaymentAccountId))
                .andReturn();
    }

    @Test
    @Order(4)
    void getPaymentById_unsuccessful_NotExistingPayment() throws Exception {
        mockMvc.perform(get("/payment/{id}", 999L)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(PaymentNotFoundException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Payment with id = 999 not found",
                        result.getResolvedException().getMessage()))
                .andReturn();
    }

    @Test
    @Order(5)
    void getAllPaymentsByAccountId_successful() throws Exception {
        mockMvc.perform(get("/payment/account")
                        .param("accountId", "1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(List.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), List.class)
                ))
                .andExpect(jsonPath("$.[0].amount").value(dbPaymentAmount))
                .andReturn();
    }

    @Test
    @Order(6)
    void getAllPaymentsByUserId_successful() throws Exception {
        mockMvc.perform(get("/payment/user")
                        .param("userId", "1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(List.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), List.class)
                ))
                .andExpect(jsonPath("$.[0].amount").value(dbPaymentAmount))
                .andReturn();

    }

    @Test
    @Order(7)
    void editPayment_successful() throws Exception {

        PaymentDTO fakePaymentDTO = new PaymentDTO();
        fakePaymentDTO.setAmount(200.0);
        fakePaymentDTO.setAccountId(1L);

        String jFakePaymentDTO = objectMapper.writeValueAsString(fakePaymentDTO);

        mockMvc.perform(put("/payment/{id}", 1L)
                        .header("Authorization", "Bearer " + token)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jFakePaymentDTO))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(PaymentDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), PaymentDTO.class)
                ))
                .andExpect(jsonPath("$.amount").value(200.0))
                .andReturn();
    }

    @Test
    @Order(8)
    void deletePayment_successful() throws Exception {
        mockMvc.perform(delete("/payment/{id}", 1L)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(result -> assertInstanceOf(PaymentDTO.class,
                        objectMapper.readValue(result.getResponse().getContentAsString(), PaymentDTO.class)
                ))
                .andExpect(jsonPath("$.amount").value(200.0))
                .andReturn();
    }
}