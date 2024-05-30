package com.sparta.controller;

import com.sparta.dto.CommentRequestDto;
import com.sparta.dto.CommentResponseDto;
import com.sparta.dto.ResponseDto;
import com.sparta.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{id}")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        try {
            return commentService.createComment(id, requestDto, request);
        } catch (Exception e) {
            return new CommentResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }

    @PatchMapping("/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto reqeustDto, HttpServletRequest request) {
        try {
            return commentService.update(id, reqeustDto, request);
        } catch (Exception e) {
            return new CommentResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteComment(@PathVariable Long id, HttpServletRequest request) {
        try {
            return commentService.deleteComment(id, request);
        } catch (Exception e) {
            return new CommentResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }
}
