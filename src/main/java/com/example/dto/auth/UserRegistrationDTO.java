package com.example.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {

    @NotBlank(message = "Name required")
    private String name;

    @NotBlank(message = "Surname required")
    private String surname;

    @NotBlank(message = "phone number required")
    private String phoneNumber;


}
