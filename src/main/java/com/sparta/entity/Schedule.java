package com.sparta.entity;



import com.sparta.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity(name="Schedule")
@NoArgsConstructor
public class Schedule extends Timestamped{
    @Id                                                         // primary_key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(nullable = false)
    private String taskTitle;

    @Column(nullable = false)
    private String taskContent;

    @Column(nullable = false)
    private String manager;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate date;


    public Schedule(ScheduleRequestDto scheduleRequestDto) {
        this.taskTitle = scheduleRequestDto.getTaskTitle();
        this.taskContent = scheduleRequestDto.getTaskContent();
        this.manager = scheduleRequestDto.getManager();
        this.password = scheduleRequestDto.getPassword();
        this.date = LocalDate.now();
    }

    public void update(ScheduleRequestDto scheduleRequestDto) {
        this.taskTitle = scheduleRequestDto.getTaskTitle();
        this.taskContent = scheduleRequestDto.getTaskContent();
        this.manager = scheduleRequestDto.getManager();
        this.password = scheduleRequestDto.getPassword();
        this.date = LocalDate.now();
    }
}
