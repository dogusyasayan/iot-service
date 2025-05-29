package com.iot_service.model.openremote;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    private String clientId;
    private String clientSecret;
}