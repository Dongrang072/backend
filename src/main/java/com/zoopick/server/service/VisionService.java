package com.zoopick.server.service;

import com.zoopick.server.config.FastApiProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.zoopick.server.dto.vision.VisionAnalyzeRequest;
import com.zoopick.server.dto.vision.VisionAnalyzeResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisionService {
    private final RestTemplate restTemplate;
    private final FastApiProperties fastApiProperties;

    public VisionAnalyzeResponse analyzeImage(String imageUrl) {
        VisionAnalyzeRequest request = new VisionAnalyzeRequest(imageUrl);
        String url = fastApiProperties.getBaseUrl() +
                fastApiProperties.getVision().getAnalyzePath();
        return restTemplate.postForObject(url, request, VisionAnalyzeResponse.class);
    }

}