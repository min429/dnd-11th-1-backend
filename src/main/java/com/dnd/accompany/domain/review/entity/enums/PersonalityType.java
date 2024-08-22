package com.dnd.accompany.domain.review.entity.enums;

public enum PersonalityType {
    KIND("친절합니다"),
    BRIGHT("밝습니다"),
    FUN("재밌습니다"),
    COMFORTABLE("편안합니다"),
    TRUSTWORTHY("믿을 수 있습니다"),
    POSITIVE("긍정적입니다"),
    SENSE("센스 있습니다"),
    EMOTIONAL("감정적입니다"),
    RATIONAL("이성적입니다"),
    PASSIONATE("열정적입니다"),
    GOOD_ATTACHMENT("애착이 좋습니다");

    private final String description;

    PersonalityType(String description) {
        this.description = description;
    }
}