package com.birthday.auth.exception.handler;

import com.birthday.auth.api.Result;
import com.birthday.auth.api.error.ErrorCode;
import com.birthday.auth.exception.TokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class TokenExceptionHandler {
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<Result<Void>> handle(TokenException e) {
        ErrorCode errorCode = e.getErrorCode();
        Result<Void> result = Result.fail(errorCode);

        log.error(result.toString());

        return ResponseEntity.status(errorCode.getHttpStatus().value())
                .body(result);
    }
}
