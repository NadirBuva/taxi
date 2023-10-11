package com.example.service;

import com.example.dto.PassportDTO;
import com.example.entities.AttachEntity;
import com.example.entities.doc.PassportEntity;
import com.example.repository.PassportRepository;
import org.springframework.stereotype.Service;

@Service
public class PassportService {

    private final PassportRepository repository ;
    private final AttachService attachService;
    public PassportService(PassportRepository repository, AttachService attachService) {
        this.repository = repository;
        this.attachService = attachService;
    }

    public String create(PassportDTO dto) {
        PassportEntity entity = new PassportEntity();

        entity.setPassportNumber(dto.getPassportNumber());
        AttachEntity passport_image = attachService.getAttach(dto.getPassport_image_id());
        return null;
    }
}
