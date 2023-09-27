package com.example.entities.auth;

import com.example.enums.Gender;
import com.example.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "profile")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    @Column
    private Gender gender;

    @Column
    private String birthday;

    @Enumerated(value = EnumType.STRING)
    @Column
    private ProfileStatus status;



    @Enumerated(value = EnumType.STRING)
    @Column
    private ProfileRole role;

    @Column
    private Boolean visible=true;

    @Column(name = "created_date")
    private LocalDateTime createdDate ;

}
