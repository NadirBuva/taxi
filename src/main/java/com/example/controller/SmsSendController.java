package com.example.controller;

import com.example.dto.PhoneNumberDTO;
import com.example.enums.Language;
import com.example.service.SmsSendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RestController
@RequestMapping("/smstestiviy")
@Tag(name = "Send sms testiviy controller", description = "This controller for send sms testiviy")
public class SmsSendController {
    private final SmsSendService service ;

    public SmsSendController(SmsSendService service) {
        this.service = service;
    }

    @Operation(summary = "Method for send sms clients", description = "This method used to send a sms ")
    @PostMapping("/send")
    private ResponseEntity<?> registration(@Valid @RequestBody PhoneNumberDTO dto,
                                                            @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
         String isSending = service.sendSms(dto);
        return ResponseEntity.ok(isSending);
    }

}
