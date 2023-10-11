package com.example.dto;

import com.example.entities.AttachEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PassportDTO {
    private Integer id;
    private String passportNumber ;
    private String passport_image_id;
    private AttachEntity passport_image;
}
