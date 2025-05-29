package com.iot_service.model.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateAssetRequest {
    private final String name;
    private final String assetType;
    private final Double lat;
    private final Double lon;
    private final String note;
    private final Integer area;
    private final Integer roomNumber;
    private final Integer imoNumber;
    private final Integer eniNumber;
    private final Double speed;
}
