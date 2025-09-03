package com.birthday.auth.annotation;

import com.birthday.auth.validator.ProfileImageValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProfileImageValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProfileImage {
    String message() default "유효하지 않은 이미지입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long maxSize() default 5 * 1024 * 1024; // 5MB 기본값

    String[] allowedTypes() default {"image/png", "image/jpeg", "image/jpg", "image/svg"};
}
