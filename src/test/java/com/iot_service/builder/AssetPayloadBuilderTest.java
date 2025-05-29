package com.iot_service.builder;

import com.iot_service.builder.strategy.AssetAttributeStrategy;
import com.iot_service.model.request.CreateAssetRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AssetPayloadBuilderTest {

    private AssetPayloadBuilder builder;
    private AssetAttributeStrategy mockStrategy;

    @BeforeEach
    void setUp() {
        mockStrategy = mock(AssetAttributeStrategy.class);
        builder = new AssetPayloadBuilder(List.of(mockStrategy));
    }

    @Test
    void it_should_build_payload_with_common_fields_and_strategy_attributes() {
        // given
        CreateAssetRequest request = CreateAssetRequest.builder()
                .name("Test Room")
                .assetType("room")
                .lat(41.0)
                .lon(29.0)
                .note("some note")
                .build();

        given(mockStrategy.supports("room")).willReturn(true);
        doAnswer(invocation -> {
            Map<String, Object> attrs = invocation.getArgument(1);
            attrs.put("area", Map.of("value", 45, "type", "positiveInteger"));
            return null;
        }).when(mockStrategy).addAttributes(eq(request), anyMap());

        // when
        Map<String, Object> payload = builder.build(request);

        // then
        assertThat(payload.get("name")).isEqualTo("Test Room");
        assertThat(payload.get("type")).isEqualTo("asset");
        assertThat(payload.get("realm")).isEqualTo("master");
        assertThat(payload.get("accessPublicRead")).isEqualTo(true);

        Map<String, Object> attributes = (Map<String, Object>) payload.get("attributes");
        assertThat(attributes.get("notes")).isEqualTo(Map.of("value", "some note", "type", "text"));
        assertThat(attributes.get("location")).isEqualTo(
                Map.of("value", Map.of("lat", 41.0, "lon", 29.0), "type", "geoPoint")
        );
        assertThat(attributes.get("assetType")).isEqualTo(Map.of("value", "room", "type", "string"));
        assertThat(attributes.get("area")).isEqualTo(Map.of("value", 45, "type", "positiveInteger"));
    }

    @Test
    void it_should_skip_strategy_if_not_supported() {
        // given
        CreateAssetRequest request = CreateAssetRequest.builder()
                .name("Simple")
                .assetType("ship")
                .build();

        given(mockStrategy.supports("ship")).willReturn(false);

        // when
        Map<String, Object> payload = builder.build(request);

        // then
        Map<String, Object> attributes = (Map<String, Object>) payload.get("attributes");
        assertThat(attributes.get("assetType")).isEqualTo(Map.of("value", "ship", "type", "string"));
        verify(mockStrategy, never()).addAttributes(any(), any());
    }
}
