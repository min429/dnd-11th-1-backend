package com.dnd.accompany.domain.user.entity.enums;

public enum TravelPreference {
    PLANNED("계획적"),
    UNPLANNED("무계획"),

    PUBLIC_MONEY("공금"),
    DUTCH_PAY("더치페이"),

    LOOKING_FOR("찾아본 곳"),
    DRAWN_TO("끌리는 곳"),

    QUICKLY("빨리빨리"),
    LEISURELY("느긋하게");

    private String description;

    TravelPreference(String description) {
        this.description = description;
    }
}
