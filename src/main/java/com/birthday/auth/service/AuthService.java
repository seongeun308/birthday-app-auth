package com.birthday.auth.service;

import com.birthday.auth.domain.dto.TokenPair;
import com.birthday.auth.domain.dto.request.LoginRequest;
import com.birthday.auth.domain.dto.request.SignupRequest;

public interface AuthService {
    void signup(SignupRequest signupRequest);

    void delete(Long userId);

    TokenPair login(LoginRequest loginRequest);

    void logout(Long userId);

    boolean isEmailExist(String email);

    void validateDuplicateLogin(String token);
}