package com.sparta.dto;

import com.sparta.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentToDto {
    private Long id;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentToDto(Comment comment){
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
