package com.example.vsharstuk.numberreducer.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {

    private HttpStatus status;
    private String message;
    private String detail;
}
