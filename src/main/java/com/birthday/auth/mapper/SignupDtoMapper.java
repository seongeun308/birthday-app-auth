package com.birthday.auth.mapper;

import com.birthday.auth.domain.dto.SignupDto;
import com.birthday.auth.domain.entity.Account;
import com.birthday.auth.encryptor.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignupDtoMapper {
    private final PasswordEncryptor passwordEncryptor;

    public Account toAccount(SignupDto dto) {
        return Account.builder()
                .email(dto.getEmail())
                .password(passwordEncryptor.encrypt(dto.getPassword()))
                .build();
    }
}
