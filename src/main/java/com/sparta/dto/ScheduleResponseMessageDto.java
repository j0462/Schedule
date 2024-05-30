package com.sparta.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ScheduleResponseMessageDto {
    private HttpStatus status;
    private String message;
    private ScheduleResponseDto data;

    public ScheduleResponseMessageDto(HttpStatus status, String message, ScheduleResponseDto data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
