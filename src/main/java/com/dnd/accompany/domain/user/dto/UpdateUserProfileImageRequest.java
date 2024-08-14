package com.dnd.accompany.domain.user.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateUserProfileImageRequest(
        @NotNull List<String> imageUrls
) {
}
