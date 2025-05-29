package com.iot_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot_service.model.request.CreateAssetRequest;
import com.iot_service.model.response.AssetResponse;
import com.iot_service.model.response.LinkResponse;
import com.iot_service.service.AssetService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AssetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AssetService assetService;

    @Test
    void it_should_create_asset() throws Exception {
        // given
        CreateAssetRequest request = CreateAssetRequest.builder()
                .name("Room 101")
                .assetType("room")
                .build();

        AssetResponse response = AssetResponse.builder()
                .id("asset-1")
                .name("Room 101")
                .build();

        // when
        mockMvc.perform(post("/api/assets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        // then
        ArgumentCaptor<CreateAssetRequest> captor = ArgumentCaptor.forClass(CreateAssetRequest.class);
        verify(assetService).createAsset(captor.capture());

        assertThat(captor.getValue().getName()).isEqualTo("Room 101");
    }

    @Test
    void it_should_get_asset_by_id() throws Exception {
        // given
        String id = "asset-1";
        AssetResponse response = AssetResponse.builder()
                .id(id)
                .name("Room 101")
                .build();

        // when
        mockMvc.perform(get("/api/assets/{id}", id))
                .andExpect(status().isOk());

        // then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(assetService).getAsset(captor.capture());

        assertThat(captor.getValue()).isEqualTo("asset-1");
    }

    @Test
    void it_should_update_asset() throws Exception {
        // given
        String id = "asset-1";
        CreateAssetRequest request = CreateAssetRequest.builder()
                .name("Updated Room")
                .assetType("room")
                .build();

        // when
        mockMvc.perform(put("/api/assets/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // then
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<CreateAssetRequest> requestCaptor = ArgumentCaptor.forClass(CreateAssetRequest.class);

        verify(assetService).updateAsset(idCaptor.capture(), requestCaptor.capture());

        assertThat(idCaptor.getValue()).isEqualTo("asset-1");
        assertThat(requestCaptor.getValue().getName()).isEqualTo("Updated Room");
    }

    @Test
    void it_should_delete_asset() throws Exception {
        // given
        String id = "asset-1";

        // when
        mockMvc.perform(delete("/api/assets/{id}", id))
                .andExpect(status().isNoContent());

        // then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(assetService).deleteAsset(captor.capture());

        assertThat(captor.getValue()).isEqualTo("asset-1");
    }

    @Test
    void it_should_get_all_asset_links() throws Exception {
        // given
        LinkResponse link = LinkResponse.builder()
                .assetId("asset-1")
                .assetName("Room 101")
                .build();

        // when
        mockMvc.perform(get("/api/assets/links"))
                .andExpect(status().isOk());

        // then
        verify(assetService).getAllAssetLinks();
    }
}