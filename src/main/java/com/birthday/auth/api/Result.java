package com.birthday.auth.api;

import com.birthday.auth.api.error.ErrorCode;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Result<T> {
    private int code;
    private int status;
    private String message;
    private List<FieldErrorDetail> errors;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(
                0,
                200,
                "성공",
                List.of(),
                data
        );
    }

    public static <T> Result<T> success() {
        return new Result<>(
                0,
                200,
                "성공",
                List.of(),
                null
        );
    }

    public static <T> Result<T> fail(ErrorCode errorCode) {
        return new Result<>(
                errorCode.getCode(),
                errorCode.getHttpStatus().value(),
                errorCode.getMessage(),
                List.of(),
                null
        );
    }

    public static <T> Result<T> fail(ErrorCode errorCode, List<FieldErrorDetail> errorDetails) {
        return new Result<>(
                errorCode.getCode(),
                errorCode.getHttpStatus().value(),
                errorCode.getMessage(),
                errorDetails,
                null
        );
    }
}
