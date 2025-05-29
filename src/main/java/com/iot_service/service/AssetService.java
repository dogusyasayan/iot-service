package com.iot_service.service;

import com.iot_service.builder.AssetPayloadBuilder;
import com.iot_service.config.client.OpenRemoteClient;
import com.iot_service.converter.AssetConverter;
import com.iot_service.converter.LinkConverter;
import com.iot_service.model.request.CreateAssetRequest;
import com.iot_service.model.response.AssetResponse;
import com.iot_service.model.response.LinkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final OpenRemoteClient openRemoteClient;
    private final AssetPayloadBuilder assetPayloadBuilder;
    private final AssetConverter assetConverter;
    private final LinkConverter linkConverter;

    public AssetResponse createAsset(CreateAssetRequest request) {
        var payload = assetPayloadBuilder.build(request);
        String response = openRemoteClient.createAsset(payload);
        return assetConverter.convert(response);
    }


    public AssetResponse getAsset(String assetId) {
        String response = openRemoteClient.getAssetById(assetId);
        return assetConverter.convert(response);
    }

    public AssetResponse updateAsset(String id, CreateAssetRequest request) {
        var payload = assetPayloadBuilder.build(request);
        openRemoteClient.updateAsset(id, payload);
        return getAsset(id);
    }

    public void deleteAsset(String id) {
        openRemoteClient.deleteAsset(id);
    }

    public List<LinkResponse> getAllAssetLinks() {
        String response = openRemoteClient.getAssetLinks("master");
        return linkConverter.convert(response);
    }
}
