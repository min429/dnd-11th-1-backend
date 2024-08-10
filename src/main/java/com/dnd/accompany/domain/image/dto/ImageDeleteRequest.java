package com.dnd.accompany.domain.image.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDeleteRequest {
    @NotBlank
    private String key;

    @NotBlank
    private String path;
}
