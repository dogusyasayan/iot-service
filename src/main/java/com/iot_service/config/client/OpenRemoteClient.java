package com.iot_service.config.client;

import com.iot_service.config.feign.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(
        name = "openremoteClient",
        url = "https://localhost/api/master",
        configuration = FeignClientConfig.class
)
public interface OpenRemoteClient {

    @PostMapping("/asset")
    String createAsset(@RequestBody Map<String, Object> payload);

    @GetMapping("/asset/{assetId}")
    String getAssetById(@PathVariable("assetId") String assetId);

    @GetMapping("/asset/user/link")
    String getAssetLinks(@RequestParam("realm") String realm);

    @PutMapping("/asset/{assetId}")
    String updateAsset(@PathVariable("assetId") String assetId, @RequestBody Map<String, Object> payload);

    @DeleteMapping(value = "/asset", params = "assetId")
    void deleteAsset(@RequestParam("assetId") String assetId);
}

