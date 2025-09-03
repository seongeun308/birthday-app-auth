package com.birthday.auth.domain.dto;

import com.birthday.auth.annotation.Password;
import com.birthday.auth.annotation.ProfileImage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
public class SignupRequest {
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;
    @Password
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;
    @ProfileImage
    private MultipartFile profileImage;
    @NotNull(message = "생일은 필수입니다.")
    private LocalDate birth;
}
