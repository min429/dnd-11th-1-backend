package com.dnd.accompany.domain.review.entity.enums;

public enum TravelStyle {
    LIKE_RESTAURANTS("맛집을 좋아합니다"),
    DOES_NOT_HAVE_TO_BE_RESTAURANT("맛집이 아니어도 괜찮습니다"),
    PREFER_HOTPLE("핫플레이스를 선호합니다"),
    LIKE_QUIET_PLACES("조용한 곳을 좋아합니다"),
    LIKE_TAKING_PICTURES("사진 찍기를 좋아합니다"),
    PREFER_TOURIST_DESTINATIONS("관광지를 선호합니다"),
    PREFER_HEALING("힐링을 선호합니다"),
    ENJOY_ACTIVITY("액티비티를 즐깁니다"),
    LIKE_SHOPPING("쇼핑을 좋아합니다"),
    LIKE_CAFES("카페를 좋아합니다");

    private final String description;

    TravelStyle(String description) {
        this.description = description;
    }
}
