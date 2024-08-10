package com.dnd.accompany.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageResponse {
    private final String key;
    private final String path;
}
