package com.example.dto.auth;

import com.example.entities.auth.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private Integer id;

    private String username;
    private String phoneNumber;
    private ProfileRole role;

    public JwtDTO(String phoneNumber, ProfileRole role) {
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
