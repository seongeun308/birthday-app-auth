package com.birthday.auth.service;

import com.birthday.auth.domain.dto.TokenPair;
import com.birthday.auth.domain.dto.SignupDto;

public interface AuthService {
    void signup(SignupDto signupDto);

    void delete(Long userId);

    TokenPair login(String email, String password);;

    void logout(Long userId);
}
