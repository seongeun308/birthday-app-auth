package com.birthday.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenType {

    ACCESS_TOKEN(15 * 60 * 60),
    REFRESH_TOKEN(7 * 24 * 60 * 60),
    ;

    private final int expiresIn;
}
