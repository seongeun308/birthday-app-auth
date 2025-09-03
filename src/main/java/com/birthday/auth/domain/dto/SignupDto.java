package com.birthday.auth.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
public class SignupDto {
    private String email;
    private String password;
    private String nickname;
    private String profileImgUrl;
    private LocalDate birth;
}
