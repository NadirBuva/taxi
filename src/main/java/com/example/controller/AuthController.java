package com.example.controller;

import com.example.dto.auth.ProfileResponseDTO;
import com.example.dto.auth.UserRegistrationDTO;
import com.example.enums.Language;
import com.example.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "Authorization Controller", description = "This controller for authorization")
public class AuthController {
//    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }


    @Operation(summary = "Method for registration", description = "This method used to create a user")
    @PostMapping("/registration")
    private ResponseEntity<ProfileResponseDTO> registration(@Valid @RequestBody UserRegistrationDTO dto,
                                                            @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
        log.info("Registration : phone {}, name {}", dto.getPhoneNumber(), dto.getName());
        ProfileResponseDTO result = service.registration(dto, language);
        return ResponseEntity.ok(result);
    }




}
