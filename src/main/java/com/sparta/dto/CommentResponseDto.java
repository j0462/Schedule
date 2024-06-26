package com.sparta.dto;

import com.sparta.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Getter
@NoArgsConstructor
@Configuration
public class CommentResponseDto extends ResponseDto{
    CommentToDto commentOne;

    public CommentResponseDto(String msg, int statusCode) {
        super(msg, statusCode);
    }

    public CommentResponseDto(String msg, int statusCode, Comment comment){
        super(msg, statusCode);
        this.commentOne = new CommentToDto(comment);
    }
}
