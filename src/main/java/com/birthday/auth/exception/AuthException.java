package com.birthday.auth.exception;

import com.birthday.auth.api.error.ErrorCode;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private final ErrorCode errorCode;

    public AuthException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
