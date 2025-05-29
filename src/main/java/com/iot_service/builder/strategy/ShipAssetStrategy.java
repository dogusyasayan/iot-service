package com.iot_service.builder.strategy;

import com.iot_service.model.request.CreateAssetRequest;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class ShipAssetStrategy implements AssetAttributeStrategy {

    @Override
    public boolean supports(String assetType) {
        return "ship".equalsIgnoreCase(assetType);
    }

    @Override
    public void addAttributes(CreateAssetRequest request, Map<String, Object> attributes) {
        if (Objects.nonNull(request.getEniNumber())) {
            attributes.put("ENINumber", Map.of("value", request.getEniNumber(), "type", "integer"));
        }
        if (Objects.nonNull(request.getImoNumber())) {
            attributes.put("IMONumber", Map.of("value", request.getImoNumber(), "type", "integer"));
        }
        if (Objects.nonNull(request.getSpeed())) {
            attributes.put("speed", Map.of("value", request.getSpeed(), "type", "number"));
        }
    }
}