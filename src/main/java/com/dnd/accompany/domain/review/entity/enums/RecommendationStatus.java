package com.dnd.accompany.domain.review.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "추천 여부", example = "SUGGESTION", allowableValues = {"SUGGESTION", "NOT_RECOMMENDED"})
public enum RecommendationStatus {
    SUGGESTION("추천합니다"),
    NOT_RECOMMENDED("추천하지 않습니다");

    private final String description;

    RecommendationStatus(String description) {
        this.description = description;
    }
}
