package com.iot_service.controller;

import com.iot_service.model.openremote.TokenResponse;
import com.iot_service.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    void it_should_return_token_response() throws Exception {
        // given
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken("abc123");

        given(authService.getToken()).willReturn(tokenResponse);

        // when
        mockMvc.perform(post("/auth/token"))
                .andExpect(status().isOk());
    }
}