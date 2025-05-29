package com.iot_service.model.openremote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenRemoteDeviceResponse {
    private String id;
    private String name;
    private String type;
}