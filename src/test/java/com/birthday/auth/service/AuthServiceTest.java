package com.birthday.auth.service;

import com.birthday.auth.domain.dto.SignupRequest;
import com.birthday.auth.domain.entity.Account;
import com.birthday.auth.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class AuthServiceTest {

    private final SignupRequest signupRequest = new SignupRequest(
            "test@email.com",
            "test",
            "kim",
            null,
            null
    );
    @Autowired
    private AuthService authService;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void signup() {
        authService.signup(signupRequest);

        Account account = accountRepository.findByEmail(signupRequest.getEmail()).orElse(null);

        assertNotNull(account);
        assertEquals(signupRequest.getEmail(), account.getEmail());
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