package com.sparta.schedule.controller;

import com.sparta.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sparta.schedule.dto.RequestDto;
import com.sparta.schedule.dto.ResponseListMessageDto;
import com.sparta.schedule.dto.ResponseMessageDto;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 전체 목록을 조회 (작성일 기준 내림차순)
    @GetMapping("/schedules")
    public ResponseListMessageDto getSchedules() {
        return scheduleService.getSchedules();
    }

    // id값과 비밀번호를 통해서 일정 조회
    @GetMapping("/schedules/{id}")
    public ResponseMessageDto selectSchedule(@PathVariable("id")Long id, @RequestParam("password") String password) {
        return scheduleService.selectSchedule(id,password);
    }

    @PostMapping("/schedules")
    public ResponseMessageDto registerSchedule(@RequestBody RequestDto requestDto) {
        return scheduleService.registerSchedule(requestDto);
    }

    @PutMapping("/schedules/{id}")
    public ResponseMessageDto updateSchedule(@PathVariable("id") Long id, @RequestBody RequestDto requestDto) {
        return scheduleService.updateSchedule(id, requestDto);
    }

    // id값과 비밀번호를 통해서 선택한 일정 삭제
    @DeleteMapping("/schedules/{id}")
    public ResponseMessageDto deleteSchedule(@PathVariable("id") Long id, @RequestParam("password") String password) {
        return scheduleService.deleteSchedule(id,password);
    }
}
