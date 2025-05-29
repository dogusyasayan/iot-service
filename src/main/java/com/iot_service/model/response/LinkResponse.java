package com.iot_service.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LinkResponse {
    private String assetId;
    private String assetName;
    private String parentAssetName;
    private String userFullName;
    private String createdOn;
}