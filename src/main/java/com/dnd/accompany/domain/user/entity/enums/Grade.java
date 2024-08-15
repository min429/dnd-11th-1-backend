package com.dnd.accompany.domain.user.entity.enums;

public enum Grade {
    ROOKIE("새싹 메이트"),
    ELITE("우수 메이트"),
    PASSIONATE("열정 메이트"),
    VETERAN("베테랑 메이트");

    private String description;

    Grade(String description) {
        this.description = description;
    }
}
