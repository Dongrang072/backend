package com.zoopick.server.dto.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zoopick.server.entity.ItemCategory;
import com.zoopick.server.entity.ItemColor;
import com.zoopick.server.entity.ItemType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CreateItemPostRequest {
    @NotBlank
    private ItemType type;
    @NotBlank
    private ItemCategory category;
    @NotBlank
    private ItemColor color;
    private String title;
    private String description;
    @JsonProperty("image_url")
    private String imageUrl;
    @NotBlank
    @JsonProperty("building_id")
    private long buildingId;
    @JsonProperty("detail_address")
    private String detailAddress;
    @JsonProperty("reported_at")
    private LocalDateTime reportedAt;
}
