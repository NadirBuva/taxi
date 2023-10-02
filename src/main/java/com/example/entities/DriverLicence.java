package com.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class DriverLicence {
    @Id
    private Integer id;

    @Column(name = "driver_license_number")
    private String passportNumber ;

    @Column(name = "driver_license_given_date")
    private LocalDate licence_given_date
            ;
    @Column(name = "passport_image_id")
    private String passport_image_id;

    @OneToOne
    @JoinColumn(name = "passport_image_id", insertable = false, updatable = false)
    private AttachEntity passport_image;
}
