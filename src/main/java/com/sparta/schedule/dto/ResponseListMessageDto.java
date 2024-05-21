package com.sparta.schedule.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ResponseListMessageDto {
    private HttpStatus status;
    private String message;
    private List<ResponseDto> data;

    public ResponseListMessageDto(HttpStatus status, String message, List<ResponseDto> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
