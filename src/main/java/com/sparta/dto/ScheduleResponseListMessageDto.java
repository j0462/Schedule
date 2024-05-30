package com.sparta.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ScheduleResponseListMessageDto {
    private HttpStatus status;
    private String message;
    private List<ScheduleResponseDto> data;

    public ScheduleResponseListMessageDto(HttpStatus status, String message, List<ScheduleResponseDto> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
