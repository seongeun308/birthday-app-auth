package com.birthday.auth.service;

import com.birthday.auth.api.error.AuthErrorCode;
import com.birthday.auth.domain.TokenType;
import com.birthday.auth.domain.dto.Token;
import com.birthday.auth.domain.dto.TokenPair;
import com.birthday.auth.domain.dto.request.LoginRequest;
import com.birthday.auth.domain.dto.request.SignupRequest;
import com.birthday.auth.domain.entity.Account;
import com.birthday.auth.encryptor.PasswordEncryptor;
import com.birthday.auth.exception.AuthException;
import com.birthday.auth.exception.TokenException;
import com.birthday.auth.mapper.SignupDtoMapper;
import com.birthday.auth.repository.AccountRepository;
import com.birthday.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncryptor passwordEncryptor;
    private final TokenService tokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final SignupDtoMapper signupDtoMapper;

    @Override
    public void signup(SignupRequest signupRequest) {
        Account account = signupDtoMapper.toAccount(signupRequest);
        accountRepository.save(account);

        // todo: 계정 생성 이벤트 발행
    }

    @Override
    public void delete(Long userId) {

    }

    @Override
    public TokenPair login(LoginRequest loginRequest) {
        Account account = accountRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthException(AuthErrorCode.ACCOUNT_NOT_FOUND));

        if (!passwordEncryptor.matches(loginRequest.getPassword(), account.getPassword())) {
            throw new AuthException(AuthErrorCode.ACCOUNT_NOT_FOUND);
        }

        Token access = tokenService.generateToken(TokenType.ACCESS_TOKEN, Map.of("accountId", account.getId()));
        Token refresh = tokenService.generateToken(TokenType.REFRESH_TOKEN, Map.of("accountId", account.getId()));

        refreshTokenRepository.save(account.getId(), refresh);

        return new TokenPair(access, refresh);
    }

    @Override
    public void logout(Long userId) {

    }

    @Override
    public boolean isEmailExist(String email) {
        return accountRepository.findByEmail(email).isPresent();
    }

    @Override
    public void validateDuplicateLogin(String token) {
        try {
            tokenService.validateToken(token);
        } catch (TokenException e) {
            return;
        }

        throw new AuthException(AuthErrorCode.DUPLICATE_LOGIN); // 유효한 토큰이면 중복 로그인
    }


}