package com.birthday.auth.api.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TokenErrorCode implements ErrorCode {
    EXPIRED(1201, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    INVALID_SIGNATURE(1202, HttpStatus.UNAUTHORIZED, "토큰 서명이 유효하지 않습니다."),
    MALFORMED(1203, HttpStatus.UNAUTHORIZED, "잘못된 토큰 형식입니다."),
    MISSING_TOKEN(1204, HttpStatus.UNAUTHORIZED, "토큰이 요청에 존재하지 않습니다."),
    PARSE_ERROR(1205, HttpStatus.UNAUTHORIZED, "토큰 파싱 중 오류가 발생했습니다.");;

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
