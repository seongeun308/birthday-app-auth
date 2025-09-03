package com.birthday.auth.controller;

import com.birthday.auth.api.Result;
import com.birthday.auth.domain.dto.request.SignupRequest;
import com.birthday.auth.domain.dto.response.CheckResponse;
import com.birthday.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
