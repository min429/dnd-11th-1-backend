package com.dnd.accompany.domain.review.entity.enums;

public enum SatisfactionLevel {
    CONTENT("만족"),
    COMMONLY("보통"),
    DISSATISFACTION("불만족");

    private final String description;

    SatisfactionLevel(String description) {
        this.description = description;
    }
}