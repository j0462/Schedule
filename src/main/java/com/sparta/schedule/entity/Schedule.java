package com.sparta.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.sparta.schedule.dto.RequestDto;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "schedule")
public class Schedule extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    @Column(name = "author", nullable = false, length = 30)
    private String author;

    @Column(name = "password", length = 30)
    private String password;

    // RequestDto에서 값이 들어올 때 -> 처음 만들 때 -> 제목, 내용, 담당자, 비밀번호
    public Schedule(RequestDto requestDto) {
        title = requestDto.getTitle();
        contents = requestDto.getContents();
        author = requestDto.getAuthor();
        password = requestDto.getPassword();
    }

    // RequestDto에서 값을 수정할 때 -> 제목, 내용, 담당자, 비밀번호
    public void updateSchedule(RequestDto requestDto) {
        title = requestDto.getTitle();
        contents = requestDto.getContents();
        author = requestDto.getAuthor();
    }
}
