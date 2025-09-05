package com.birthday.auth.service;

import com.birthday.auth.api.error.TokenErrorCode;
import com.birthday.auth.domain.TokenType;
import com.birthday.auth.domain.dto.Token;
import com.birthday.auth.exception.TokenException;
import io.jsonwebtoken.*;
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
    public void validateToken(String token) {
        parseToken(token);
    }

    private Claims parseToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new TokenException(TokenErrorCode.MISSING_TOKEN);
        }

        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException ignored) {
            throw new TokenException(TokenErrorCode.EXPIRED);
        } catch (SecurityException ignored) {
            throw new TokenException(TokenErrorCode.INVALID_SIGNATURE);
        } catch (MalformedJwtException ignored) {
            throw new TokenException(TokenErrorCode.MALFORMED);
        } catch (JwtException ignored) {
            throw new TokenException(TokenErrorCode.PARSE_ERROR);
        }
    }
}
