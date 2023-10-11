package com.example.entities.auth;

import com.example.entities.AttachEntity;
import com.example.entities.doc.DriverLicence;
import com.example.entities.doc.PassportEntity;
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
    private String middleName ;

    @Column
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    @Column
    private Gender gender;

    @Column
    private String birthday;

    @Enumerated(value = EnumType.STRING)
    @Column
    private ProfileStatus status=ProfileStatus.NOT_ACTIVE;

    @Enumerated(value = EnumType.STRING)
    @Column
    private ProfileRole role;

    @Column(name = "driver_image_id")
    private String driver_image_id;
    @ManyToOne
    @JoinColumn(name = "driver_image_id", insertable = false, updatable = false)
    private AttachEntity driver_image;

    @Column(name = "passport_id")
    private Integer passport_id;
    @OneToOne
    @JoinColumn(name = "passport_id", insertable = false, updatable = false)
    private PassportEntity passport;

    @Column(name = "driver_license_id")
    private Integer driver_license_id;
    @OneToOne
    @JoinColumn(name = "driver_license_id", insertable = false, updatable = false)
    private DriverLicence driverLicence;

    @Column
    private Boolean visible=true;

    @Column(name = "created_date")
    private LocalDateTime createdDate ;

}
