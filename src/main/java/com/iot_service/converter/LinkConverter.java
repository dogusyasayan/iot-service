package com.iot_service.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot_service.model.response.LinkResponse;
import com.iot_service.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LinkConverter {

    private final ObjectMapper objectMapper;

    public List<LinkResponse> convert(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);
            List<LinkResponse> result = new ArrayList<>();

            for (JsonNode node : root) {
                result.add(LinkResponse.builder()
                        .assetId(node.path("id").path("id").asText())
                        .assetName(node.path("assetName").asText())
                        .parentAssetName(node.path("parentAssetName").asText())
                        .userFullName(node.path("userFullName").asText())
                        .createdOn(DateUtils.epochMillisToString(node.path("createdOn").asLong()))
                        .build());
            }

            return result;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse link response", e);
        }
    }
}
