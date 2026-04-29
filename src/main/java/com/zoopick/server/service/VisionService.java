package com.zoopick.server.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.zoopick.server.dto.vision.VisionAnalyzeRequest;
import com.zoopick.server.dto.vision.VisionAnalyzeResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisionService {
    private final RestTemplate restTemplate;

    // fast api 주소
    private final String FASTAPI_URL = "http://localhost:8000/vision/analyze";

    public VisionAnalyzeResponse analyzeImage(String imageUrl) {
        VisionAnalyzeRequest request = new VisionAnalyzeRequest(imageUrl);
        return restTemplate.postForObject(FASTAPI_URL, request, VisionAnalyzeResponse.class);
    }

}
