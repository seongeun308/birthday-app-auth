package com.birthday.auth.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class LoginResponse {
    private final String accessToken;
    private final String expireAt;
}
