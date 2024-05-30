package com.sparta.service;

import com.sparta.dto.CommentRequestDto;
import com.sparta.dto.CommentResponseDto;
import com.sparta.dto.ResponseDto;
import com.sparta.entity.Comment;
import com.sparta.entity.Schedule;
import com.sparta.entity.User;
import com.sparta.entity.UserRoleEnum;
import com.sparta.jwt.JwtUtil;
import com.sparta.repository.CommentRepository;
import com.sparta.repository.ScheduleRepository;
import com.sparta.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // Blog Comment Create Function
    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token이 유효하지않습니다.");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Schedule schedule = scheduleRepository.findById(id);

            Comment comment = commentRepository.saveAndFlush(new Comment(requestDto, schedule));

            return new CommentResponseDto("success", HttpStatus.OK.value(), comment);
        } else {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }
    }

    public CommentResponseDto update(Long id, CommentRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);

                User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                        () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
                );

                UserRoleEnum userRoleEnum = user.getRole();
                Comment comment;

                if (userRoleEnum == UserRoleEnum.USER) {
                    comment = commentRepository.findById(id).orElseThrow(
                            () -> new NullPointerException("해당 댓글은 존재하지 않습니다.")
                    );

                    String loginUserName = user.getUsername();
                    if (!comment.getUsername().equals(loginUserName)) {
                        throw new IllegalArgumentException("회원님의 댓글이 아니므로 업데이트 할 수 없습니다.");
                    }
                } else {
                    comment = commentRepository.findById(id).orElseThrow(
                            () -> new NullPointerException("해당 댓글은 존재하지 않습니다.")
                    );
                    comment.update(requestDto);
                }
                Comment saveComment = commentRepository.save(comment);
                return new CommentResponseDto("sucess", HttpStatus.OK.value(), saveComment);
            }
        } else {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }
        return null;
    }

    public ResponseDto deleteComment(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            UserRoleEnum userRoleEnum = user.getRole();
            Comment comment;

            if (userRoleEnum == UserRoleEnum.USER) {
                comment = commentRepository.findById(id).orElseThrow(
                        () -> new NullPointerException("해당 댓글은 존재하지 않습니다.")
                );

                String loginUserName = user.getUsername();
                if (!comment.getUsername().equals(loginUserName)) {
                    throw new IllegalArgumentException("회원님의 댓글이 아니므로 삭제할 수 없습니다.");
                }
            } else {
                comment = commentRepository.findById(id).orElseThrow(
                        () -> new NullPointerException("해당 댓글은 존재하지 않습니다.")
                );
                commentRepository.deleteById(id);
            }
            return new ResponseDto("sucess", HttpStatus.OK.value());
        } else {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }
    }
}
