package com.sparta.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
public class SignupRequestDto {         // 회원가입용 RequestDto

    @NotBlank(message = "아이디를 입력해주세요")
    @Pattern(regexp="^[a-z0-9]{4,10}$", message="아이디를 4~10자로 입력해주세요.(특수문자x)")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}$", message="비밀번호를 8~15자로 입력해주세요.(특수문자o)")
    private String password;

    private boolean admin = false;
    private String adminToken = "";
}
