package com.sparta.dto;

import com.sparta.entity.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ScheduleResponseDto {
    private Long scheduleId;
    private String taskTitle;
    private String taskContent;
    private String manager;
    private LocalDate date;

    public ScheduleResponseDto(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.taskTitle = schedule.getTaskTitle();
        this.taskContent = schedule.getTaskContent();
        this.manager = schedule.getManager();
        this.date = schedule.getDate();
    }

    public ScheduleResponseDto(Long id, String taskTitle, String taskContent, String manager, String password, LocalDate formatdate) {
        this.scheduleId = id;
        this.taskTitle = taskTitle;
        this.taskContent = taskContent;
        this.manager = manager;
        this.date = formatdate;
    }
}
