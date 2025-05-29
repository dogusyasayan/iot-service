package com.iot_service.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot_service.model.response.AssetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AssetConverter {

    private final ObjectMapper objectMapper;


    public AssetResponse convert(String responseJson) {
        try {
            JsonNode root = objectMapper.readTree(responseJson);

            return AssetResponse.builder()
                    .id(root.path("id").asText())
                    .name(root.path("name").asText())
                    .type(root.path("type").asText())
                    .attributes(objectMapper.convertValue(
                            root.path("attributes"),
                            new TypeReference<Map<String, Object>>() {
                            }
                    ))
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse asset response", e);
        }
    }
}