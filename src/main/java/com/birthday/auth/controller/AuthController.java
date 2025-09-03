package com.birthday.auth.controller;

import com.birthday.auth.api.Result;
import com.birthday.auth.domain.dto.SignupRequest;
import com.birthday.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
