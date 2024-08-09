package com.dnd.accompany.domain.user.dto;

import lombok.Getter;

@Getter
public record CreateUserProfileResponse(
        Long userId
) {
}
