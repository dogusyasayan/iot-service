package com.iot_service.builder;

import com.iot_service.builder.strategy.AssetAttributeStrategy;
import com.iot_service.model.request.CreateAssetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AssetPayloadBuilder {

    private final List<AssetAttributeStrategy> attributeStrategies;

    public Map<String, Object> build(CreateAssetRequest request) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("name", request.getName());
        payload.put("type", "asset");
        payload.put("realm", "master");
        payload.put("accessPublicRead", true);
        Map<String, Object> attributes = new LinkedHashMap<>();

        addIfPresent(attributes, "notes", request.getNote(), "text");

        if (request.getLat() != null && request.getLon() != null) {
            attributes.put("location", Map.of(
                    "value", Map.of("lat", request.getLat(), "lon", request.getLon()),
                    "type", "geoPoint"
            ));
        }

        attributes.put("assetType", Map.of("value", request.getAssetType(), "type", "string"));

        // Strategy pattern: adds dynamic attributes based on asset type
        attributeStrategies.stream()
                .filter(s -> s.supports(request.getAssetType()))
                .findFirst()
                .ifPresent(s -> s.addAttributes(request, attributes));

        payload.put("attributes", attributes);
        return payload;
    }

    private void addIfPresent(Map<String, Object> target, String key, Object value, String type) {
        if (value != null) {
            target.put(key, Map.of("value", value, "type", type));
        }
    }
}
