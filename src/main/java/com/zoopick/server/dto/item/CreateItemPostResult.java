package com.zoopick.server.dto.item;

import com.zoopick.server.entity.ItemStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateItemPostResult {
    @NotBlank
    private long itemId;
    @NotBlank
    private ItemStatus itemStatus;
    private String message;
}
