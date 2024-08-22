package com.dnd.accompany.domain.review.entity.enums;

public enum CompanionType {
    ALL_ACCOMPANYING("전체 동행"),
    PARTIAL_COMPANIONSHIP("부분 동행"),
    ACCOMMODATION_SHARING("숙소 공유"),
    ACCOMPANYING_TOUR("투어 동행");

    private String description;

    CompanionType(String description) {
        this.description = description;
    }
}
