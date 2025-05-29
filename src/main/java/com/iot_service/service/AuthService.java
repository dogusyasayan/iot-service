package com.iot_service.service;

import com.iot_service.model.openremote.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${openremote.token-url}")
    private String tokenUrl;

    @Value("${openremote.client-id}")
    private String clientId;

    @Value("${openremote.client-secret}")
    private String clientSecret;

    private final WebClient.Builder webClientBuilder;

    private TokenResponse cachedToken;
    private Instant tokenExpiresAt;

    public synchronized TokenResponse getToken() {
        if (isValidToken()) {
            return cachedToken;
        }

        TokenResponse newToken = fetchNewToken();
        cacheToken(newToken);
        return newToken;
    }

    private boolean isValidToken() {
        return cachedToken != null
                && tokenExpiresAt != null
                && Instant.now().isBefore(tokenExpiresAt.minusSeconds(60));
    }

    private TokenResponse fetchNewToken() {
        String body = "grant_type=client_credentials" +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret;

        return webClientBuilder.build()
                .post()
                .uri(tokenUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .block();
    }

    private void cacheToken(TokenResponse token) {
        this.cachedToken = token;
        this.tokenExpiresAt = Instant.now().plusSeconds(token.getExpiresIn());
    }
}
