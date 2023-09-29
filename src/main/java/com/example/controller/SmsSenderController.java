package com.example.controller;

import com.example.dto.PhoneNumberDTO;
import com.example.service.SMSSender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/sms")
@Tag(name = "sms sender Controller", description = "This controller for sms")
public class SmsSenderController {

    private final SMSSender service ;

    public SmsSenderController(SMSSender service) {
        this.service = service;
    }

    @Operation(summary = "Method for send sms hali ishlamaydigani  ", description = "This method used to send sms")
    @PostMapping("/sendsms")
    private ResponseEntity<String> registration(@Valid @RequestBody PhoneNumberDTO phone ) {
        log.info("Registration : phone {} ", phone );
        String result = service.sendSms(phone);
        return ResponseEntity.ok(result);
    }

}
