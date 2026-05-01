package com.zoopick.server.dto.item;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ListItemPostResult {
    @NotBlank
    private List<ItemPostRecord> itemPosts;
    @NotBlank
    private int total;
    @NotBlank
    private int page;
}
