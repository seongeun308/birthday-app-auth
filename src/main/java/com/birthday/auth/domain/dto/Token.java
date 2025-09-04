package com.birthday.auth.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Token {
    private String token;
    private String expireAt;
}
