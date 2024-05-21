package com.sparta.schedule.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseMessageDto {

    private HttpStatus status;
    private String message;
    private ResponseDto data;

    public ResponseMessageDto(HttpStatus status, String message, ResponseDto data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
