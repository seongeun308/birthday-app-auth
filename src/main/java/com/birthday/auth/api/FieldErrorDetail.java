package com.birthday.auth.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.FieldError;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class FieldErrorDetail {
    private String field;
    private String message;

    public static FieldErrorDetail from(FieldError error) {
        return new FieldErrorDetail(error.getField(), error.getDefaultMessage());
    }
}
