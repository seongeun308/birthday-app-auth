package com.birthday.auth.validator;

import com.birthday.auth.annotation.ProfileImage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class ProfileImageValidator implements ConstraintValidator<ProfileImage, MultipartFile> {

    private long maxSize;
    private String[] allowedTypes;

    @Override
    public void initialize(ProfileImage constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
        this.allowedTypes = constraintAnnotation.allowedTypes();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null) return true;

        if (file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("이미지 파일이 비어있습니다.")
                    .addConstraintViolation();
            return false;
        }

        if (file.getSize() > maxSize) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("이미지 용량이 허용 가능한 범위를 벗어났습니다. (최대 " + maxSize + "MB)")
                    .addConstraintViolation();
            return false;
        }

        if (!Arrays.asList(allowedTypes).contains(file.getContentType())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("이미지 확장자가 유효하지 않습니다.")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
