package com.iot_service.config.feign;

import com.iot_service.service.AuthService;
import feign.Client;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Configuration
@RequiredArgsConstructor
public class FeignClientConfig {

    private final AuthService authService;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return (RequestTemplate template) -> {
            String token = authService.getToken().getAccessToken();
            template.header("Authorization", "Bearer " + token);
            template.header("Content-Type", "application/json");
            template.header("Accept", "application/json");
        };
    }

    @Bean
    public Client feignClient() throws Exception {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }

            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new SecureRandom());

        return new Client.Default(sslContext.getSocketFactory(), (hostname, session) -> true);
    }
}