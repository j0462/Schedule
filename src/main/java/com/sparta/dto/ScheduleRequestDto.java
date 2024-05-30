package com.sparta.dto;


import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String taskTitle;
    private String taskContent;
    private String manager;
    private String password;
}
