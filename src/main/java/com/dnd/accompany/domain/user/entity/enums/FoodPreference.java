package com.dnd.accompany.domain.user.entity.enums;

public enum FoodPreference {
    MEAT("육류"),
    SEAFOOD("해산물"),

    RICE("밥류"),
    VEGETABLES("채소"),

    COFFEE("커피"),
    DESSERT("디저트"),

    FAST_FOOD("패스트푸드"),
    STREET_FOOD("스트릿푸드");

    private String description;

    FoodPreference(String description) {
        this.description = description;
    }
}
