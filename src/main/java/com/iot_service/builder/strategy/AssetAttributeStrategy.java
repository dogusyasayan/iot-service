package com.iot_service.builder.strategy;

import com.iot_service.model.request.CreateAssetRequest;

import java.util.Map;

public interface AssetAttributeStrategy {
    boolean supports(String assetType);

    void addAttributes(CreateAssetRequest request, Map<String, Object> attributes);
}