package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSendSms {
    private boolean success ;
    private String message ;
    private String content ;
}
