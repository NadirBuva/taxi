package com.example.controller;

import com.example.enums.Language;
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

    private SMSSender service ;

    @Operation(summary = "Method for send sms ", description = "This method used to send sms")
    @PostMapping("/registration")
    private ResponseEntity<String> registration(@PathVariable("phone") String phone,
                                                            @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
        log.info("Registration : phone {} ", phone );
        String result = service.sendSMS(phone, language);
        return ResponseEntity.ok(result);
    }

}
