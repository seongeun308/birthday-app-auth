package com.birthday.auth.service;

import com.birthday.auth.domain.TokenType;
import com.birthday.auth.domain.dto.Token;

import java.util.Map;

public interface TokenService {
    Token generateToken(TokenType type, Map<String, Object> claims);

    void validateToken(Token token);
}
