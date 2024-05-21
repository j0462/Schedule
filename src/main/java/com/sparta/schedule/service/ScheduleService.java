package com.sparta.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sparta.schedule.dto.RequestDto;
import com.sparta.schedule.dto.ResponseDto;
import com.sparta.schedule.dto.ResponseListMessageDto;
import com.sparta.schedule.dto.ResponseMessageDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.schedule.status.HttpMessage;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ResponseListMessageDto getSchedules() {
        return new ResponseListMessageDto(HttpStatus.OK, HttpMessage.CHECK_SUCCESS,
                scheduleRepository.findAllByOrderByCreatedAtDesc().stream().map(ResponseDto::new)
                        .toList());
    }

    public ResponseMessageDto registerSchedule(RequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ResponseMessageDto(HttpStatus.CREATED,HttpMessage.CREATE_SUCCESS,
                new ResponseDto(savedSchedule));
    }

    public ResponseMessageDto selectSchedule(Long id, String password) {
        Object data = checkData(id,password);

        if(data instanceof Schedule)
            return new ResponseMessageDto(HttpStatus.OK,HttpMessage.CHECK_SUCCESS,
                    new ResponseDto((Schedule) data));

        return (ResponseMessageDto) data;
    }

    public ResponseMessageDto deleteSchedule(Long id, String password) {
        Object data = checkData(id,password);

        if(data instanceof Schedule) {
            scheduleRepository.delete((Schedule)data);
            return new ResponseMessageDto(HttpStatus.OK,HttpMessage.DELETE,
                    new ResponseDto((Schedule)data));
        }

        return (ResponseMessageDto) data;
    }

    @Transactional
    public ResponseMessageDto updateSchedule(Long id, RequestDto requestDto) {
        Object data = checkData(id,requestDto.getPassword());

        if(data instanceof Schedule) {
            ((Schedule) data).updateSchedule(requestDto);
            return new ResponseMessageDto(HttpStatus.OK, HttpMessage.UPDATE_SCHEDULE,
                    new ResponseDto((Schedule) data));
        }

        return (ResponseMessageDto) data;
    }


    private Schedule findById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 데이터가 존재하지 않습니다.")
        );
    }

    private void passwordCheck(Schedule schedule,String password) {
        if(schedule.getPassword() == null)      // 만약 비밀번호를 처음 세팅할때 없었다면 , 수정을 해도 비밀번호는 영원히 없는거로..
            return;

        if(schedule.getPassword().equals(password))
            return;

        throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    private Object checkData(Long id, String password) {
        try {
            Schedule findSchedule = findById(id);
            passwordCheck(findSchedule,password);
            return findSchedule;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

            if(e.getMessage().equals("해당 데이터가 존재하지 않습니다."))
                return new ResponseMessageDto(HttpStatus.NOT_FOUND, HttpMessage.NOT_FOUND,
                        null);

            return new ResponseMessageDto(HttpStatus.BAD_REQUEST, HttpMessage.PASSWORD_ERROR,
                        null);
        }
    }
}
