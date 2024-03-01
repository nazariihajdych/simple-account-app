package ua.ithillel.app.model.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ua.ithillel.app.model.enums.Gender;

import java.util.Date;
import java.util.List;

@Data
public class AccountDTO {

    private Long id;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy")
    private Date dateOfBirth;
    private String country;
    private Double balance;
    private Gender gender;
    private Long user_id;
    private List<PaymentDTO> payments;
}
