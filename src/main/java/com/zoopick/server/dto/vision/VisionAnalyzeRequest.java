package com.zoopick.server.dto.vision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VisionAnalyzeRequest {
    private String imageUrl;
}
