package com.sparta.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.dto.RequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long scheduleId;

    private String taskTitle;
    private String taskContent;
    private String manager;
    private String password;
    private LocalDate date;


    public Schedule(RequestDto requestDto) {
        this.taskTitle = requestDto.getTaskTitle();
        this.taskContent = requestDto.getTaskContent();
        this.manager = requestDto.getManager();
        this.password = requestDto.getPassword();
        this.date = LocalDate.now();
    }

    public void update(RequestDto requestDto) {
        this.taskTitle = requestDto.getTaskTitle();
        this.taskContent = requestDto.getTaskContent();
        this.manager = requestDto.getManager();
        this.password = requestDto.getPassword();
        this.date = LocalDate.now();
    }
}
