package com.birthday.auth;

import org.springframework.http.ResponseCookie;

import java.time.Duration;
import java.time.LocalDateTime;

public class TokenUtils {
    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
    private static final String HEADER_STRING = "Authorization";
    private static final String PREFIX = "Bearer ";
    private static final String SAME_SITE = "Strict";
    private static final String PATH = "/reissue";

    public static Duration getExpireDuration(String expireAt) {
        LocalDateTime now = LocalDateTime.now();
        return Duration.between(now, LocalDateTime.parse(expireAt));
    }

    public static ResponseCookie createHttpOnlyCookie(String refreshToken, Duration expireDuration) {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .httpOnly(true)
                .secure(false)  // Todo : 배포 시 .secure(true)로 변경
                .sameSite(SAME_SITE)
                .path(PATH)
                .maxAge(expireDuration)
                .build();
    }


}
