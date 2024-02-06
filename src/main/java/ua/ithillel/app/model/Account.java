package ua.ithillel.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Integer id;
    private String firstName;
    private String lastName;
    private String country;
    private Double balance;
    private String gender;
}
