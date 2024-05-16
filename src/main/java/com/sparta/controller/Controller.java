package com.sparta.controller;


import com.sparta.dto.RequestDto;
import com.sparta.dto.ResponseDto;
import com.sparta.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api")

public class Controller {
    private final JdbcTemplate jdbcTemplate;
    public Controller(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/schedule")
    public ResponseDto createSchedule(@RequestBody RequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO schedule (taskTitle, taskContent, manager, password, localdate) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getTaskTitle());
                    preparedStatement.setString(2, schedule.getTaskContent());
                    preparedStatement.setString(3, schedule.getManager());
                    preparedStatement.setString(4, schedule.getPassword());

                    String formatDate = schedule.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    preparedStatement.setString(5, formatDate);
                    return preparedStatement;
                },
                keyHolder);
        Long id = keyHolder.getKey().longValue();
        schedule.setScheduleId(id);

        ResponseDto responseDto = new ResponseDto(schedule);

        return responseDto;
    }

    @GetMapping("/schedule")
    public List<ResponseDto> getSchedules() {
        // DB 조회
        String sql = "SELECT * FROM schedule";

        return jdbcTemplate.query(sql, new RowMapper<ResponseDto>() {
            @Override
            public ResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String taskTitle = rs.getString("taskTitle");
                String taskContent = rs.getString("taskContent");
                String manager = rs.getString("manager");
                String password = rs.getString("password");
                String date = rs.getString("localdate");
                LocalDate formatdate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return new ResponseDto(id, taskTitle, taskContent, manager, password, formatdate);
            }
        });
    }

    @PutMapping("/schedule/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody RequestDto requestDto) {
        String password = requestDto.getPassword();
        // 해당 메모가 DB에 존재하는지 확인
        Schedule schedule = findById(id);
        if(schedule != null) {
            if (schedule.getPassword().equals(password)) {
                String sql = "UPDATE schedule SET taskTitle = ?, taskContent = ?, manager = ? WHERE id = ?";
                jdbcTemplate.update(sql, requestDto.getTaskTitle(), requestDto.getTaskContent(), requestDto.getManager(), id);
            }else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/schedule/{id}")
    public Long deleteMemo(@PathVariable Long id, @RequestBody RequestDto requestDto) {
        String password = requestDto.getPassword();

        // 해당 메모가 DB에 존재하는지 확인
        Schedule schedule = findById(id);
        if (schedule != null) {
            // 비밀번호가 일치하는지 확인
            if (schedule.getPassword().equals(password)) {
                String sql = "DELETE FROM schedule WHERE id = ?";
                jdbcTemplate.update(sql, id);
                return id;
            } else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    private Schedule findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM schedule WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setTaskTitle(resultSet.getString("taskTitle"));
                schedule.setTaskContent(resultSet.getString("taskContent"));
                schedule.setManager(resultSet.getString("manager"));
                schedule.setPassword(resultSet.getString("password"));
                schedule.setDate(LocalDate.parse(resultSet.getString("localdate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                return schedule;
            } else {
                return null;
            }
        }, id);
    }

}
