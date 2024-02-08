package ua.ithillel.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {
    private Integer id;
    private String firstName;
    private String lastName;
    private String country;
    private Double balance;
    private String gender;

    public Account(String firstName, String lastName, String country, Double balance, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.balance = balance;
        this.gender = gender;
    }
}
