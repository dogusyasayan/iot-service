package com.iot_service.builder.strategy;

import com.iot_service.model.request.CreateAssetRequest;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class RoomAssetStrategy implements AssetAttributeStrategy {

    @Override
    public boolean supports(String assetType) {
        return "room".equalsIgnoreCase(assetType);
    }

    @Override
    public void addAttributes(CreateAssetRequest request, Map<String, Object> attributes) {
        if (Objects.nonNull(request.getArea())) {
            attributes.put("area", Map.of("value", request.getArea(), "type", "positiveInteger"));
        }
        if (Objects.nonNull(request.getRoomNumber())) {
            attributes.put("roomNumber", Map.of("value", request.getRoomNumber(), "type", "positiveInteger"));
        }
    }
}