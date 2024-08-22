package com.dnd.accompany.domain.review.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "만족도", example = "GOOD", allowableValues = {"GOOD", "COMMONLY", "DISSATISFACTION"})
public enum SatisfactionLevel {
    GOOD("만족"),
    COMMONLY("보통"),
    DISSATISFACTION("불만족");

    private final String description;

    SatisfactionLevel(String description) {
        this.description = description;
    }
}