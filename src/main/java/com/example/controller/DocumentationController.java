package com.example.controller;
import com.example.dto.PassportDTO;
import com.example.dto.PhoneNumberDTO;
import com.example.service.LicenceService;
import com.example.service.PassportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/docx")
@Tag(name = "Verifying document controller", description = "This controller for check of passport and driver license")
public class DocumentationController {
        private final PassportService passportService;
        private final LicenceService licenceService;

    public DocumentationController(PassportService passportService, LicenceService licenceService) {
        this.passportService = passportService;
        this.licenceService = licenceService;
    }

    @Operation(summary = "Method for getting attribute of driver's passport", description = "This method used to attribute of driver's passport")
    @PostMapping("/passport")
    private ResponseEntity<String> registration(@Valid @RequestBody PassportDTO dto ) {
        log.info("Get passport number : dto {} ", dto );
        String result = passportService.create(dto);
        return ResponseEntity.ok(result);
    }
}
