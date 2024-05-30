package com.sparta.controller;

import com.sparta.dto.LoginRequestDto;
import com.sparta.dto.ResponseDto;
import com.sparta.dto.SignupRequestDto;
import com.sparta.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    @ResponseBody
    @PostMapping("/signup")
    public ResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        try {
            return userService.signup(signupRequestDto);
        } catch (Exception e) {
            return new ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            return userService.login(loginRequestDto, response);
        } catch (Exception e) {
            return new ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }
}
