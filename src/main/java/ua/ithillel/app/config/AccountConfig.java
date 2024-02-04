package ua.ithillel.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.ithillel.app.model.Account;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AccountConfig {

    @Bean
    public List<Account> accounts(){

        List<Account> accountsList = new ArrayList<>(List.of(
                new Account(1, "Jack", "Boldom", "Canada", 2000.0, "M"),
                new Account(2, "Sara", "Emmanek", "USA", 5000.0, "W"),
                new Account(3, "Laurentia", "Vademach", "Canada", 3000.0, "W"),
                new Account(4, "Vasil", "Horobyn", "Ukraine", 1300.0, "M")
        ));

        return accountsList;
    }
}
