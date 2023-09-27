package com.example.dto.product.attach;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AttachResponseDTO {
    private String id;
    private String originalName;
    private String path;
    private Long size;
    private String extension;
    private LocalDateTime createdData;
    private String url;
}
