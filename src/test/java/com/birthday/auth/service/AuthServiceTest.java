package com.birthday.auth.service;

import com.birthday.auth.domain.dto.SignupDto;
import com.birthday.auth.domain.entity.Account;
import com.birthday.auth.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthServiceTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private AccountRepository accountRepository;

    private final SignupDto signupDto = new SignupDto(
            "test@email.com",
            "test",
            "kim", "", null);

    @Test
    void signup() {
        authService.signup(signupDto);

        Account account = accountRepository.findByEmail(signupDto.getEmail()).orElse(null);

        assertNotNull(account);
        assertEquals(signupDto.getEmail(), account.getEmail());
    }

    @Test
    void delete() {
    }

    @Test
    void login() {
    }

    @Test
    void logout() {
    }
}