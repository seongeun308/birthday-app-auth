package com.birthday.auth.service;

import com.birthday.auth.domain.TokenType;
import com.birthday.auth.domain.dto.Token;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

@Service
public class JwtTokenService implements TokenService {

    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    @Override
    public Token generateToken(TokenType type, Map<String, Object> claims) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresIn = now.plusSeconds(type.getExpiresIn());
        Date expiration = Date.from(expiresIn.atZone(ZoneId.systemDefault()).toInstant());

        String token = Jwts.builder()
                .signWith(secretKey)
                .expiration(expiration)
                .claims(claims)
                .compact();

        return new Token(token, expiresIn.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    @Override
    public void validateToken(Token token) {

    }
}
