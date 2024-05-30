package com.sparta.controller;


import com.sparta.dto.ScheduleRequestDto;
import com.sparta.dto.ScheduleResponseListMessageDto;
import com.sparta.dto.ScheduleResponseMessageDto;
import com.sparta.service.WebService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")

public class ScheduleController {
    private final WebService webService;
    public ScheduleController(WebService webService) {this.webService = webService;}

    @PostMapping("/schedule")
    public ScheduleResponseMessageDto createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return webService.createSchedule(scheduleRequestDto);
    }

    @GetMapping("/schedule")
    public ScheduleResponseListMessageDto getSchedules() {
        return webService.checkSchedules();
    }

    @PutMapping("/schedule/{id}")
    public ScheduleResponseMessageDto updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        return webService.putSchedule(id, scheduleRequestDto);
    }

    @DeleteMapping("/schedule/{id}")
    public ScheduleResponseMessageDto deleteMemo(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        return webService.deleteSchedule(id, scheduleRequestDto);
    }
}
