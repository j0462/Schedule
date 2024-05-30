package com.sparta.repository;

import com.sparta.dto.ScheduleRequestDto;
import com.sparta.dto.ScheduleResponseDto;
import com.sparta.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Component
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Schedule insertValue(Schedule schedule) {
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
        return schedule;
    }

    public List<ScheduleResponseDto> check() {
        // DB 조회
        String sql = "SELECT * FROM schedule";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String taskTitle = rs.getString("taskTitle");
                String taskContent = rs.getString("taskContent");
                String manager = rs.getString("manager");
                String password = rs.getString("password");
                String date = rs.getString("localdate");
                LocalDate formatdate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return new ScheduleResponseDto(id, taskTitle, taskContent, manager, password, formatdate);
            }
        });
    }

    public void put(Long id, ScheduleRequestDto scheduleRequestDto) {
        String sql = "UPDATE schedule SET taskTitle = ?, taskContent = ?, manager = ? WHERE id = ?";
        jdbcTemplate.update(sql, scheduleRequestDto.getTaskTitle(), scheduleRequestDto.getTaskContent(), scheduleRequestDto.getManager(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Schedule findById(Long id) {
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
