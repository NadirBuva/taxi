package com.example.service;

import com.example.dto.auth.*;
import com.example.entities.auth.ProfileEntity;
import com.example.entities.auth.ProfileRole;
import com.example.enums.Language;
import com.example.enums.ProfileStatus;
import com.example.exp.PhoneAlreadyExistsException;
import com.example.exp.ItemNotFoundException;
import com.example.exp.ProfileNotFoundException;
import com.example.repository.ProfileCustomRepository;
import com.example.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProfileService {
    private final ProfileRepository repository;
//    private final EmailHistoryService emailHistoryService;
//    private final MailService mailService;


    private final ProfileCustomRepository customRepository;
    private final ResourceBundleService resourceBundleService;

    public ProfileService(ProfileRepository repository, ProfileCustomRepository customRepository, ResourceBundleService resourceBundleService) {
        this.repository = repository;
//        this.emailHistoryService = emailHistoryService;
//        this.mailService = mailService;
        this.customRepository = customRepository;
        this.resourceBundleService = resourceBundleService;
    }

    public ProfileEntity get(Integer id) {
        return repository.findById(id).orElseThrow(() -> {
            log.warn("Profile not found id = {}", id);
            throw new ItemNotFoundException(resourceBundleService.getMessage("item.not.found", Language.RU, id));
        });
    }

    public ProfileResponseDTO create(AdminRegistrationDTO dto) {

        ProfileEntity entity = new ProfileEntity();
        entity.setRole(dto.getRole());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhoneNumber(dto.getPhoneNumber());


        entity.setStatus(ProfileStatus.ACTIVE);

        repository.save(entity);

//        Thread thread = new Thread() {
//            @Override
//            public synchronized void start() {
//                String sb = "Salom qalaysan \n" +
//                        "Bu test message" +
//                        "Click the link : http://localhost:8080/auth/verification/email/" +
//                        JwtUtil.encode(entity.getEmail(),entity.getRole());
//                mailService.sendEmail(dto.getEmail(), "Complete Registration", sb);
//
//                EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity();
//                emailHistoryEntity.setEmail(dto.getEmail());
//                emailHistoryEntity.setMessage(sb);
//                emailHistoryEntity.setCreatedDate(LocalDateTime.now());
//                emailHistoryService.create(emailHistoryEntity);
//            }
//        };
//        thread.start();

        return getDTO(entity);
    }

    private void checkEmail(String email) {

        Optional<ProfileEntity> exists = repository.findByPhoneNumber(email);
        if (exists.isPresent()) {
            ProfileEntity entity = exists.get();
            if (entity.getStatus().equals(ProfileStatus.NOT_ACTIVE)) {
                repository.delete(entity);
            } else {
                throw new PhoneAlreadyExistsException("Email already exists");
            }
        }
    }


    private ProfileResponseDTO getDTO(ProfileEntity entity) {

        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto ;
    }


    public ProfileResponseDTO updateUser(UserProfileUpdateDTO dto, Integer id) {


        ProfileEntity entity = getById(id);

        entity.setSurname(dto.getSurname());
        entity.setName(dto.getName());

        repository.save(entity);
        ProfileResponseDTO profileDTO = new ProfileResponseDTO();
        profileDTO.setName(dto.getName());
        profileDTO.setSurname(dto.getSurname());

        return profileDTO;

    }

    public ProfileResponseDTO updateAdmin(AdminProfileUpdateDTO dto) {

        ProfileEntity entity = getById(dto.getId());
        entity.setSurname(dto.getSurname());
        entity.setName(dto.getName());

        repository.save(entity);
        ProfileResponseDTO profileDTO = new ProfileResponseDTO();
        profileDTO.setName(dto.getName());
        profileDTO.setSurname(dto.getSurname());
        return profileDTO;

    }

    public Page<ProfileResponseDTO> getListClient() {

        Pageable pageable = PageRequest.of(0,10);

        Page<ProfileEntity> pageObj = repository.findAll(pageable);

        List<ProfileEntity> content = pageObj.getContent();

        List<ProfileResponseDTO> dtoList = new ArrayList<>();

        for (ProfileEntity entity : content) {
            if (entity.getRole().equals(ProfileRole.USER)){
            dtoList.add(getDTO(entity));
            }
        }

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }
    public Page<ProfileResponseDTO> getList() {

        Pageable pageable = PageRequest.of(0,10);

        Page<ProfileEntity> pageObj = repository.findAll(pageable);

        List<ProfileEntity> content = pageObj.getContent();

        List<ProfileResponseDTO> dtoList = new ArrayList<>();

        for (ProfileEntity entity : content) {
            if (entity.getRole().equals(ProfileRole.DRIVER)){
                dtoList.add(getDTO(entity));
            }
        }

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    public Boolean deleteById(Integer id) {

        repository.deleteById(id);

        return true;
    }


    public ProfileEntity getById(Integer id) {
        Optional<ProfileEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new ProfileNotFoundException("Bunday profile mavjud emas");
        }
        return optional.get();
    }

    public ProfileResponseDTO getFullDTO(ProfileEntity entity) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(entity.getId());
        dto.setRole(entity.getRole());
        dto.setVisible(entity.getVisible());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setSurname(entity.getSurname());
        dto.setName(entity.getName());
        dto.setPhoneNumber(entity.getPhoneNumber());

        return dto;
    }

    public List<ProfileDTO> filter(ProfileFilterDTO filterDTO, int page, int size) {
        Page<ProfileEntity> list = customRepository.filter(filterDTO, page, size);

        for (ProfileEntity entity : list) {
            System.out.println(entity.getName());
        }
        return null;
    }

    public String getName(String supplier_name) {
        Optional<ProfileEntity> byName = repository.findByName(supplier_name);
        if (byName.isEmpty()) {
            throw new ProfileNotFoundException("name = "+ byName +" is not found");
        }
        return byName.get().getName() ;
    }




}
