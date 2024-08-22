package com.dnd.accompany.domain.review.entity.enums;

public enum RecommendationStatus {
    SUGGESTION("추천합니다"),
    NOT_RECOMMENDED("추천하지 않습니다");

    private final String description;

    RecommendationStatus(String description) {
        this.description = description;
    }
}
