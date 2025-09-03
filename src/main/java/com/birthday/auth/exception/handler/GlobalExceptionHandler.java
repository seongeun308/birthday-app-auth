package com.birthday.auth.exception.handler;

import com.birthday.auth.api.FieldErrorDetail;
import com.birthday.auth.api.Result;
import com.birthday.auth.api.error.AuthErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldErrorDetail> errorDetails = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldErrorDetail::from)
                .toList();

        AuthErrorCode errorCode = AuthErrorCode.VALIDATION_ERROR;
        Result<Void> result = Result.fail(errorCode, errorDetails);

        log.error("{}", result);

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(result);
    }
}
