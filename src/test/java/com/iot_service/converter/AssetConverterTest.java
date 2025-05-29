package com.iot_service.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot_service.model.response.AssetResponse;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AssetConverterTest {

    private final AssetConverter converter = new AssetConverter(new ObjectMapper());

    @Test
    void it_should_convert_json_to_asset_response() {
        // given
        String json = """
                {
                  "id": "asset-1",
                  "name": "Room 101",
                  "type": "asset",
                  "attributes": {
                    "notes": {
                      "value": "This is a note",
                      "type": "text"
                    }
                  }
                }
                """;

        // when
        AssetResponse result = converter.convert(json);

        // then
        assertThat(result.getId()).isEqualTo("asset-1");
        assertThat(result.getName()).isEqualTo("Room 101");
        assertThat(result.getType()).isEqualTo("asset");

        Map<String, Object> attributes = result.getAttributes();
        assertThat(attributes).containsKey("notes");
    }

    @Test
    void it_should_throw_exception_when_invalid_json() {
        // given
        String invalidJson = "{ this is not valid json }";

        // when & then
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            converter.convert(invalidJson);
        });
    }
}
