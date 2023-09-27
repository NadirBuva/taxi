package com.example.dto.auth;

import com.example.entities.auth.ProfileRole;
import com.example.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileFilterDTO {

    private Integer id;
    private String name;
    private String surname;
    private ProfileStatus status;
    private ProfileRole role;
    private String email;
    private LocalDate fromDate;
    private LocalDate toDate;

}
