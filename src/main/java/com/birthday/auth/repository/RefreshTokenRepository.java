package com.birthday.auth.repository;

import com.birthday.auth.domain.dto.Token;

public interface RefreshTokenRepository {
    void save(Long accountId, Token token);

    boolean existsById(Long accountId);
}
