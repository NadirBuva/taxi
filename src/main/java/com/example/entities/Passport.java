package com.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "passport_number")
    private String passportNumber ;
    @Column(name = "passport_image_id")
    private String passport_image_id;
    @ManyToOne
    @JoinColumn(name = "passport_image_id", insertable = false, updatable = false)
    private AttachEntity passport_image;
}
