package com.example.dto.auth;


import com.example.entities.auth.ProfileRole;
import com.example.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponseDTO {

    private Integer id;
    private String name;
    private String surname;

    private String phoneNumber;
    private ProfileStatus status;
    private ProfileRole role;
    private Boolean visible;
    private LocalDateTime createdDate;

    public ProfileResponseDTO(Integer id, String name, String surname, ProfileStatus status, ProfileRole role, Boolean visible, LocalDateTime createdDate) {
    }
}


