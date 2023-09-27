package com.example.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserProfileUpdateDTO {

    @NotBlank(message = "Name required")
    private String name;

    @NotBlank(message = "Surname required")
    private String surname;

    @Size(min = 8, message = "Password required")
    private String password;
}
