package com.iot_service.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot_service.model.response.LinkResponse;
import com.iot_service.util.DateUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LinkConverterTest {

    private final LinkConverter converter = new LinkConverter(new ObjectMapper());

    @Test
    void it_should_convert_json_to_link_response_list() {
        // given
        long now = System.currentTimeMillis();
        String json = """
                [
                  {
                    "id": { "id": "asset-1" },
                    "assetName": "Room 101",
                    "parentAssetName": "Building A",
                    "userFullName": "John Doe",
                    "createdOn": %d
                  }
                ]
                """.formatted(now);

        // when
        List<LinkResponse> result = converter.convert(json);

        // then
        assertThat(result).hasSize(1);
        LinkResponse item = result.get(0);
        assertThat(item.getAssetId()).isEqualTo("asset-1");
        assertThat(item.getAssetName()).isEqualTo("Room 101");
        assertThat(item.getParentAssetName()).isEqualTo("Building A");
        assertThat(item.getUserFullName()).isEqualTo("John Doe");
        assertThat(item.getCreatedOn()).isEqualTo(DateUtils.epochMillisToString(now));
    }

    @Test
    void it_should_throw_exception_when_invalid_json() {
        // given
        String json = "invalid";

        // then
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            converter.convert(json);
        });
    }
}
