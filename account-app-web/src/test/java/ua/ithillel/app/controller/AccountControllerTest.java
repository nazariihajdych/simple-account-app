package ua.ithillel.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.ithillel.app.config.WebConfigTest;
import ua.ithillel.app.exeption.AccountNotFoundException;
import ua.ithillel.app.exeption.GlobalExceptionHandler;
import ua.ithillel.app.service.AccountService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfigTest.class})
@WebAppConfiguration
class AccountControllerTest {

    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AccountController(accountService))
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void findNotExistingAccount() throws Exception {
        mockMvc.perform(get("/api/account/999"))
                .andExpect(result -> {
                    assertTrue(result.getResolvedException() instanceof AccountNotFoundException);
                })
               // .andExpect(result -> assertEquals("User not found", result.getResolvedException().getMessage()))
                .andReturn();
    }
}