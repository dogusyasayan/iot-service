package com.iot_service.builder.strategy;

import com.iot_service.model.request.CreateAssetRequest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ShipAssetStrategyTest {

    private final ShipAssetStrategy strategy = new ShipAssetStrategy();

    @Test
    void it_should_support_ship_asset_type() {
        // when
        boolean result = strategy.supports("ship");

        // then
        assertThat(result).isTrue();
    }

    @Test
    void it_should_add_ship_attributes() {
        // given
        CreateAssetRequest request = CreateAssetRequest.builder()
                .eniNumber(123)
                .imoNumber(456)
                .speed(25.5)
                .build();

        Map<String, Object> attributes = new HashMap<>();

        // when
        strategy.addAttributes(request, attributes);

        // then
        assertThat(attributes).containsEntry("ENINumber", Map.of("value", 123, "type", "integer"));
        assertThat(attributes).containsEntry("IMONumber", Map.of("value", 456, "type", "integer"));
        assertThat(attributes).containsEntry("speed", Map.of("value", 25.5, "type", "number"));
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
