//package ua.ithillel.app.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import ua.ithillel.app.config.WebConfigTest;
//import ua.ithillel.app.exception.AccountNotFoundException;
//import ua.ithillel.app.exception.GlobalExceptionHandler;
//import ua.ithillel.app.model.dto.AccountDTO;
//import ua.ithillel.app.model.enums.Gender;
//import ua.ithillel.app.service.AccountService;
//
//import java.text.SimpleDateFormat;
//import java.util.HashMap;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertInstanceOf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {WebConfigTest.class})
//@WebAppConfiguration
//class AccountControllerTest {
//
//    MockMvc mockMvc;
//
//    @Autowired
//    AccountService accountService;
//
//    @BeforeEach
//    void setUp() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new AccountController(accountService))
//                .setControllerAdvice(new GlobalExceptionHandler()).build();
//    }
//
//    @Test
//    void findNotExistingAccount() throws Exception {
//        mockMvc.perform(get("/account/999"))
//                .andExpect(result -> assertInstanceOf(AccountNotFoundException.class, result.getResolvedException()))
//                .andExpect(result -> assertEquals("Account with id = 999 not found",
//                        result.getResolvedException().getMessage()))
//                .andReturn();
//    }
//
//    @Test
//    void passingNotValidFieldValues() throws Exception {
//
//        HashMap<String, String> expectedValues = new HashMap<>();
//        expectedValues.put("firstName", "should be more then one character");
//        expectedValues.put("lastName", "should be more then one character");
//        expectedValues.put("balance", "should be positive number or zero");
//        expectedValues.put("dateOfBirth", "should be in past");
//        expectedValues.put("user_id", "should be positive number");
//
//
//        AccountDTO mockAccountDTO = new AccountDTO();
//        mockAccountDTO.setFirstName("");
//        mockAccountDTO.setLastName("");
//        mockAccountDTO.setDateOfBirth(new SimpleDateFormat("ddMMyyyy").parse("01012028"));
//        mockAccountDTO.setCountry("Ukraine");
//        mockAccountDTO.setBalance((double) -10);
//        mockAccountDTO.setGender(Gender.MALE);
//        mockAccountDTO.setUser_id(0L);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonAccountDTO = objectMapper.writeValueAsString(mockAccountDTO);
//
//        mockMvc.perform(post("/account")
//                        .characterEncoding("utf-8")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonAccountDTO))
//                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException()))
//                .andExpect(result -> assertEquals(expectedValues,
//                        objectMapper.readValue(result.getResponse().getContentAsString(), HashMap.class)))
//                .andReturn();
//    }
//}