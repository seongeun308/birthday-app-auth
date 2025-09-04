package com.birthday.auth.controller;

import com.birthday.auth.TokenUtils;
import com.birthday.auth.api.Result;
import com.birthday.auth.domain.dto.Token;
import com.birthday.auth.domain.dto.TokenPair;
import com.birthday.auth.domain.dto.request.LoginRequest;
import com.birthday.auth.domain.dto.request.SignupRequest;
import com.birthday.auth.domain.dto.response.CheckResponse;
import com.birthday.auth.domain.dto.response.LoginResponse;
import com.birthday.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {



    private final AuthService authService;

    @PostMapping("/signup")
    public Result<Void> signup(@Valid @ModelAttribute SignupRequest signupRequest) {
        authService.signup(signupRequest);

        return Result.success();
    }

    @GetMapping("/check-email")
    public Result<CheckResponse> checkEmail(@RequestParam String email) {
        boolean isAvailable = !authService.isEmailExist(email);
        CheckResponse checkResponse = new CheckResponse(isAvailable);

        return Result.success(checkResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<Result<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenPair tokenPair = authService.login(loginRequest.getEmail(), loginRequest.getPassword());

        Token accessToken = tokenPair.getAccessToken();
        Token refreshToken = tokenPair.getRefreshToken();

        Duration duration = TokenUtils.getExpireDuration(refreshToken.getExpireAt());
        ResponseCookie cookie = TokenUtils.createHttpOnlyCookie(refreshToken.getToken(), duration);

        LoginResponse loginResponse = new LoginResponse(accessToken.getToken(), accessToken.getExpireAt());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Result.success(loginResponse));
    }
}
