package com.iot_service.controller;

import com.iot_service.model.request.CreateAssetRequest;
import com.iot_service.model.response.AssetResponse;
import com.iot_service.model.response.LinkResponse;
import com.iot_service.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssetResponse createAsset(@RequestBody CreateAssetRequest request) {
        return assetService.createAsset(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AssetResponse getAsset(@PathVariable String id) {
        return assetService.getAsset(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AssetResponse updateAsset(@PathVariable String id, @RequestBody CreateAssetRequest request) {
        return assetService.updateAsset(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAsset(@PathVariable String id) {
        assetService.deleteAsset(id);
    }

    @GetMapping("/links")
    public List<LinkResponse> getAllAssetLinks() {
        return assetService.getAllAssetLinks();
    }
}
