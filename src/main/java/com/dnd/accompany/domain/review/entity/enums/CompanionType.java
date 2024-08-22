package com.dnd.accompany.domain.review.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Type of companion", example = "ALL_ACCOMPANYING", allowableValues = {"ALL_ACCOMPANYING", "PARTIAL_COMPANIONSHIP", "ACCOMMODATION_SHARING", "ACCOMPANYING_TOUR"})
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
