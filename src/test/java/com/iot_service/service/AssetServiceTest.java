package com.iot_service.service;

import com.iot_service.builder.AssetPayloadBuilder;
import com.iot_service.config.client.OpenRemoteClient;
import com.iot_service.converter.AssetConverter;
import com.iot_service.converter.LinkConverter;
import com.iot_service.model.request.CreateAssetRequest;
import com.iot_service.model.response.AssetResponse;
import com.iot_service.model.response.LinkResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AssetServiceTest {

    @Mock
    private OpenRemoteClient openRemoteClient;

    @Mock
    private AssetPayloadBuilder assetPayloadBuilder;

    @Mock
    private AssetConverter assetConverter;

    @Mock
    private LinkConverter linkConverter;

    @InjectMocks
    private AssetService assetService;

    @Test
    void it_should_create_asset() {
        // given
        CreateAssetRequest request = CreateAssetRequest.builder().name("Room 101").build();
        Map<String, Object> payload = Map.of("key", "value");
        String jsonResponse = "{\"id\":\"asset-1\",\"name\":\"Room 101\"}";
        AssetResponse assetResponse = AssetResponse.builder().id("asset-1").name("Room 101").build();

        given(assetPayloadBuilder.build(request)).willReturn(payload);
        given(openRemoteClient.createAsset(payload)).willReturn(jsonResponse);
        given(assetConverter.convert(jsonResponse)).willReturn(assetResponse);

        // when
        AssetResponse result = assetService.createAsset(request);

        // then
        assertThat(result.getId()).isEqualTo("asset-1");
        assertThat(result.getName()).isEqualTo("Room 101");
    }

    @Test
    void it_should_get_asset_by_id() {
        // given
        String jsonResponse = "{\"id\":\"asset-1\",\"name\":\"Room 101\"}";
        AssetResponse response = AssetResponse.builder().id("asset-1").name("Room 101").build();

        given(openRemoteClient.getAssetById("asset-1")).willReturn(jsonResponse);
        given(assetConverter.convert(jsonResponse)).willReturn(response);

        // when
        AssetResponse result = assetService.getAsset("asset-1");

        // then
        assertThat(result.getName()).isEqualTo("Room 101");
    }

    @Test
    void it_should_get_all_asset_links() {
        // given
        String jsonResponse = "{}";
        LinkResponse link = LinkResponse.builder().assetId("asset-1").assetName("Room 101").build();

        given(openRemoteClient.getAssetLinks("master")).willReturn(jsonResponse);
        given(linkConverter.convert(jsonResponse)).willReturn(List.of(link));

        // when
        List<LinkResponse> result = assetService.getAllAssetLinks();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAssetId()).isEqualTo("asset-1");
    }

    @Test
    void it_should_update_asset() {
        // given
        CreateAssetRequest request = CreateAssetRequest.builder().name("Updated Room").build();
        Map<String, Object> payload = Map.of("key", "value");
        String jsonResponse = "{\"id\":\"asset-1\",\"name\":\"Updated Room\"}";
        AssetResponse response = AssetResponse.builder().id("asset-1").name("Updated Room").build();

        given(assetPayloadBuilder.build(request)).willReturn(payload);
        given(openRemoteClient.getAssetById("asset-1")).willReturn(jsonResponse);
        given(assetConverter.convert(jsonResponse)).willReturn(response);

        // when
        AssetResponse result = assetService.updateAsset("asset-1", request);

        // then
        assertThat(result.getName()).isEqualTo("Updated Room");
    }

    @Test
    void it_should_delete_asset() {
        // when
        assetService.deleteAsset("asset-1");

        // then
        verify(openRemoteClient).deleteAsset("asset-1");
    }
}
