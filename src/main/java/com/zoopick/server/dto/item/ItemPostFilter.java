package com.zoopick.server.dto.item;

import com.zoopick.server.entity.ItemCategory;
import com.zoopick.server.entity.ItemColor;
import com.zoopick.server.entity.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemPostFilter {
    private ItemStatus status;
    private ItemCategory category;
    private ItemColor color;
}
