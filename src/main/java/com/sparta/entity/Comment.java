package com.sparta.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "Schedule_ID", nullable = false)
    @JsonIgnore
    private Schedule schedule;


    public Comment(CommentRequestDto requestDto, Schedule schedule) {
        this.contents = requestDto.getContents();
        this.username = requestDto.getUsername();
        this.schedule = schedule;
    }

    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();
        this.username = requestDto.getUsername();
    }
}
