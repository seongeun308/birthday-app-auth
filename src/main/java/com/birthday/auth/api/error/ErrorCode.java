package com.birthday.auth.api.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    int getCode();

    HttpStatus getHttpStatus();

    String getMessage();
}
