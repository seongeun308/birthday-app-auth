package com.birthday.auth;

import com.birthday.auth.api.error.AuthErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthApplicationTests {

    private final String BASE_URL = "/auth";

    @Autowired
    private MockMvc mockMvc;

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
}
