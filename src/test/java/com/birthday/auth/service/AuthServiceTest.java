package com.birthday.auth.service;

import com.birthday.auth.api.error.AuthErrorCode;
import com.birthday.auth.domain.dto.TokenPair;
import com.birthday.auth.domain.dto.request.SignupRequest;
import com.birthday.auth.domain.entity.Account;
import com.birthday.auth.exception.AuthException;
import com.birthday.auth.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @AfterEach()
    void clear() {
        stringRedisTemplate.getConnectionFactory().getConnection().flushDb();
    }

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
    void 로그인_성공_시_토큰들_반환() {
        signup();

        TokenPair tokenPair = authService.login(signupRequest.getEmail(), signupRequest.getPassword());

        assertNotNull(tokenPair);
        assertNotNull(tokenPair.getAccessToken());
        assertNotNull(tokenPair.getRefreshToken());
    }

    @Test
    void 로그인_시_존재하지_않은_계정일_경우_AuthException_발생() {
        AuthException exception = assertThrows(AuthException.class, () ->
                authService.login(signupRequest.getEmail(), signupRequest.getPassword())
        );

        assertEquals(AuthErrorCode.ACCOUNT_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void logout() {
    }

    @Test
    void isExistEmail() {
        authService.signup(signupRequest);

        boolean isExist = authService.isEmailExist(signupRequest.getEmail());
        boolean isNotExist = authService.isEmailExist("notExist@email.com");

        assertTrue(isExist);
        assertFalse(isNotExist);
    }
}