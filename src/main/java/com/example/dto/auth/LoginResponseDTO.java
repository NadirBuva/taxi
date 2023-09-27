package com.example.dto.auth;

import com.example.entities.auth.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

    private String name;
    private String surname;
    private ProfileRole role;
    private String token;

}
