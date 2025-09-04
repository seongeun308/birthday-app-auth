package com.birthday.auth.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class LoginRequest {
    @NotBlank(message = "이메일은 필수입니다.")
    private final String email;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private final String password;
}