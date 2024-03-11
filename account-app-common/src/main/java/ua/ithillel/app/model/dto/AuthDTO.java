package ua.ithillel.app.model.dto;

import lombok.Data;

@Data
public class AuthDTO {
    private UserDTO userDTO;
    private String token;
}
