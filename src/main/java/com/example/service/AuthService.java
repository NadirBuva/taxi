package com.example.service;
import com.example.dto.auth.*;
import com.example.entities.auth.ProfileEntity;
import com.example.entities.auth.ProfileRole;
import com.example.enums.Language;
import com.example.enums.ProfileStatus;
import com.example.exp.EmailAlreadyExistsException;
import com.example.exp.LoginOrPasswordWrongException;
import com.example.exp.StatusBlockException;
import com.example.repository.ProfileRepository;
import com.example.utill.JwtUtil;
import com.example.utill.MD5;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    private final ProfileRepository repository;

    private final ResourceBundleService resourceBundleService;

    public AuthService(ProfileRepository repository, ResourceBundleService resourceBundleService) {
        this.repository = repository;
        this.resourceBundleService = resourceBundleService;
    }


    public ProfileResponseDTO registration(UserRegistrationDTO dto, Language language) {

        Optional<ProfileEntity> exists = repository.findByPhoneNumber(dto.getPhoneNumber());
        if (exists.isPresent()) {
            ProfileEntity entity = exists.get();
            if (entity.getStatus().equals(ProfileStatus.NOT_ACTIVE)) {
                repository.delete(entity);
            } else {
                throw new EmailAlreadyExistsException(resourceBundleService.getMessage("email.exists", language));
            }
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhoneNumber(dto.getPhoneNumber());

        entity.setVisible(false);
        entity.setStatus(ProfileStatus.NOT_ACTIVE);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setRole(ProfileRole.USER);

        repository.save(entity);

//        Thread thread = new Thread() {
//            @Override
//            public synchronized void start() {
//                String sb = "Salom qalaysan \n" +
//                        "Bu test message" +
//                        "Click the link : http://localhost:8080/auth/verification/email/" +
//                        JwtUtil.encode(entity.getEmail(), ProfileRole.USER);
//                mailService.sendEmail(dto.getEmail(), "Complete Registration", sb);
//
//                EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity();
//                emailHistoryEntity.setEmail(dto.getEmail());
//                emailHistoryEntity.setMessage(sb);
//                emailHistoryEntity.setCreatedDate(LocalDateTime.now());
//
//                emailHistoryService.create(emailHistoryEntity);
//            }
//        };
//        thread.start();

        return getDTO(entity);

    }


    public ProfileResponseDTO getDTO(ProfileEntity entity) {

        ProfileResponseDTO profileDTO = new ProfileResponseDTO();
        profileDTO.setId(entity.getId());
        profileDTO.setName(entity.getName());
        profileDTO.setSurname(entity.getSurname());
        profileDTO.setPhoneNumber(entity.getPhoneNumber());
        profileDTO.setStatus(entity.getStatus());
        profileDTO.setRole(entity.getRole());
        profileDTO.setVisible(entity.getVisible());
        profileDTO.setCreatedDate(entity.getCreatedDate());

        return profileDTO;
    }


    public ProfileEntity getEntity(UserRegistrationDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());

        return entity;
    }


}
