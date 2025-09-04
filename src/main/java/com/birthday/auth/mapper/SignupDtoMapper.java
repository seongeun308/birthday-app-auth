package com.birthday.auth.mapper;

import org.springframework.stereotype.Component;

import com.birthday.auth.domain.dto.request.SignupRequest;
import com.birthday.auth.domain.entity.Account;
import com.birthday.auth.encryptor.PasswordEncryptor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SignupDtoMapper {

    private final PasswordEncryptor passwordEncryptor;

    public Account toAccount(SignupRequest dto) {
        return Account.builder()
                .email(dto.getEmail())
                .password(passwordEncryptor.encrypt(dto.getPassword()))
                .build();
    }
}