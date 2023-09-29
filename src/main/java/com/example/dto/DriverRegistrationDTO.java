package com.example.dto;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class DriverRegistrationDTO {
    private String phoneNumber ;
    private String name ;
    private String surname ;
    private String birthday;
    private String driver_image ;
}
