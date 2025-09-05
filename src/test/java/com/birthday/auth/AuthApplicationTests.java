package com.birthday.auth;

import com.birthday.auth.api.error.AuthErrorCode;
import com.birthday.auth.domain.dto.TokenPair;
import com.birthday.auth.domain.dto.request.LoginRequest;
import com.birthday.auth.domain.dto.request.SignupRequest;
import com.birthday.auth.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthApplicationTests {

    private final String BASE_URL = "/auth";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthService authService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @AfterEach()
    void clear() {
        stringRedisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    @Test
    void 회원가입_성공하면_200_반환() throws Exception {
        mockMvc.perform(multipart(BASE_URL + "/signup")
                        .param("email", "test@email.com")
                        .param("password", "test1234!")
                        .param("nickname", "kim")
                        .param("birth", LocalDate.of(2025, 9, 3).toString())
                )
                .andExpect(status().isOk());
    }

    @Test
    void 회원가입_유효성_검사_실패하면_400_반환() throws Exception {
        MockMultipartFile mockProfileImage = new MockMultipartFile(
                "profileImage",
                "dummy.pdf",
                "application/pdf",
                new byte[0]
        );

        mockMvc.perform(multipart(BASE_URL + "/signup")
                        .file(mockProfileImage)
                        .param("email", "test")
                        .param("password", "test")
                        .param("nickname", "")
                        .param("birth", "")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", equalTo(AuthErrorCode.VALIDATION_ERROR.getCode())))
                .andExpect(jsonPath("$.errors", hasSize(5)));
    }

    @Test
    void 이메일_존재하면_available_false() throws Exception {
        SignupRequest signupRequest = new SignupRequest(
                "test@email.com",
                "test123!!",
                "kim",
                null,
                null
        );
        authService.signup(signupRequest);

        mockMvc.perform(get(BASE_URL + "/check-email")
                        .queryParam("email", "test@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.available", equalTo(false)));
    }

    @Test
    void 이메일_존재하지_않으면_available_true() throws Exception {
        mockMvc.perform(get(BASE_URL + "/check-email")
                        .queryParam("email", "test@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.available", equalTo(true)));
    }

    @Test
    void 로그인_성공하면_200_반환() throws Exception {
        SignupRequest signupRequest = new SignupRequest(
                "test@email.com",
                "test123!!",
                "kim",
                null,
                null
        );
        authService.signup(signupRequest);

        LoginRequest loginRequest = new LoginRequest(signupRequest.getEmail(), signupRequest.getPassword());
        mockMvc.perform(post(BASE_URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(loginRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken", notNullValue()))
                .andExpect(cookie().exists("refreshToken"));
    }

    @Test
    void 로그인_시_존재하지_않는_계정이면_400_반환() throws Exception {
        LoginRequest loginRequest = new LoginRequest("123", "123");
        mockMvc.perform(post(BASE_URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(loginRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message", equalTo(AuthErrorCode.ACCOUNT_NOT_FOUND.getMessage())));
    }

    @Test
    void 중복_로그인_시_409_반환() throws Exception {
        SignupRequest signupRequest = new SignupRequest(
                "test@email.com",
                "test123!!",
                "kim",
                null,
                null
        );
        authService.signup(signupRequest);
        LoginRequest loginRequest = new LoginRequest(signupRequest.getEmail(), signupRequest.getPassword());
        TokenPair tokenPair = authService.login(loginRequest);

        mockMvc.perform(post(BASE_URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + tokenPair.getAccessToken().getToken())
                        .content(objectMapper.writeValueAsBytes(loginRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", equalTo(AuthErrorCode.DUPLICATE_LOGIN.getMessage())));
    }
}
