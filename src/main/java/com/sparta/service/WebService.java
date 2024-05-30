package com.sparta.service;

import com.sparta.dto.ScheduleRequestDto;
import com.sparta.dto.ScheduleResponseDto;
import com.sparta.dto.ScheduleResponseListMessageDto;
import com.sparta.dto.ScheduleResponseMessageDto;
import com.sparta.entity.Schedule;
import com.sparta.repository.ScheduleRepository;
import com.sparta.status.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class WebService {
    private final ScheduleRepository scheduleRepository;

    public WebService(ScheduleRepository scheduleRepository) {this.scheduleRepository = scheduleRepository;}

    public ScheduleResponseMessageDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(scheduleRequestDto);

        Schedule savedSchedule = scheduleRepository.insertValue(schedule);

        return new ScheduleResponseMessageDto(HttpStatus.CREATED, HttpMessage.CREATE_SUCCESS,
                new ScheduleResponseDto(savedSchedule));
    }

    public ScheduleResponseListMessageDto checkSchedules() {
        return new ScheduleResponseListMessageDto(HttpStatus.OK, HttpMessage.CHECK_SUCCESS,
                scheduleRepository.check());
    }

    public ScheduleResponseMessageDto putSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        String password = scheduleRequestDto.getPassword();
        // 해당 메모가 DB에 존재하는지 확인
        Object schedule = scheduleRepository.findById(id);
        if(schedule != null) {
            if (scheduleRepository.findById(id).getPassword().equals(password)) {
                scheduleRepository.put(id, scheduleRequestDto);
            }else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            return (ScheduleResponseMessageDto) schedule;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    public ScheduleResponseMessageDto deleteSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        String password = scheduleRequestDto.getPassword();
        // 해당 메모가 DB에 존재하는지 확인
        Object schedule = scheduleRepository.findById(id);
        if (schedule != null) {
            // 비밀번호가 일치하는지 확인
            if (scheduleRepository.findById(id).getPassword().equals(password)) {
                scheduleRepository.delete(id);
                return (ScheduleResponseMessageDto) schedule;
            } else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
