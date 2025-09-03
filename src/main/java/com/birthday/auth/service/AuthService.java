package com.birthday.auth.service;

import com.birthday.auth.domain.dto.SignupRequest;
import com.birthday.auth.domain.dto.TokenPair;

public interface AuthService {
    void signup(SignupRequest signupRequest);

    void delete(Long userId);

    TokenPair login(String email, String password);

    ;

    void logout(Long userId);
}
