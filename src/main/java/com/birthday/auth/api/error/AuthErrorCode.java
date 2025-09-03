package com.birthday.auth.api.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    VALIDATION_ERROR(1001, HttpStatus.BAD_REQUEST, "입력값 검증에 실패했습니다"),
    ;

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
