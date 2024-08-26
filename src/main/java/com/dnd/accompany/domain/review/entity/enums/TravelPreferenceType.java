package com.dnd.accompany.domain.review.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Travel preference", example = "PLANNED", allowableValues = {"PLANNED", "SPONTANEOUS", "PUBLIC_MONEY_CONVENIENT", "DUTCH_PAY", "DILIGENT", "RELAXED"})
public enum TravelPreferenceType {
    PLANNED("계획적입니다"),
    SPONTANEOUS("즉흥적입니다"),
    PUBLIC_MONEY_CONVENIENT("공동 비용이 편합니다"),
    DUTCH_PAY("더치페이 합니다"),
    DILIGENT("부지런합니다"),
    RELAXED("여유롭습니다");

    private final String description;

    TravelPreferenceType(String description) {
        this.description = description;
    }
}