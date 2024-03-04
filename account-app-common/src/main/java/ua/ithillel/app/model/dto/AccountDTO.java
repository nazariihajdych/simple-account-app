package ua.ithillel.app.model.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import ua.ithillel.app.model.enums.Gender;

import java.util.Date;
import java.util.List;

@Data
public class AccountDTO {

    private Long id;
    @NotNull(message = "should not be null")
    @Size(min = 2, message = "should be more then one character")
    private String firstName;
    @NotNull(message = "should not be null")
    @Size(min = 2, message = "should be more then one character")
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy")
    @Past(message = "should be in past")
    private Date dateOfBirth;
    private String country;
    @PositiveOrZero(message = "should be positive number or zero")
    private Double balance;
    private Gender gender;
    @NotNull(message = "should not be null")
    @Positive(message = "should be positive number")
    private Long user_id;
    private List<PaymentDTO> payments;
}
