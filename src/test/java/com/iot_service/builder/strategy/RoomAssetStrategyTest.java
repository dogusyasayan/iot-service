package com.iot_service.builder.strategy;

import com.iot_service.model.request.CreateAssetRequest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RoomAssetStrategyTest {

    private final RoomAssetStrategy strategy = new RoomAssetStrategy();

    @Test
    void it_should_support_room_asset_type() {
        // when
        boolean result = strategy.supports("room");

        // then
        assertThat(result).isTrue();
    }

    @Test
    void it_should_add_room_attributes() {
        // given
        CreateAssetRequest request = CreateAssetRequest.builder()
                .area(45)
                .roomNumber(2)
                .build();

        Map<String, Object> attributes = new HashMap<>();

        // when
        strategy.addAttributes(request, attributes);

        // then
        assertThat(attributes).containsEntry("area", Map.of("value", 45, "type", "positiveInteger"));
        assertThat(attributes).containsEntry("roomNumber", Map.of("value", 2, "type", "positiveInteger"));
    }

    @Test
    void it_should_not_add_null_attributes() {
        // given
        CreateAssetRequest request = CreateAssetRequest.builder().build();
        Map<String, Object> attributes = new HashMap<>();

        // when
        strategy.addAttributes(request, attributes);

        // then
        assertThat(attributes).isEmpty();
    }
}
