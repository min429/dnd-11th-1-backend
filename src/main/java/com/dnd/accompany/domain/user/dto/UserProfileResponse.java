package com.dnd.accompany.domain.user.dto;

import com.dnd.accompany.domain.user.entity.User;
import com.dnd.accompany.domain.user.entity.UserProfile;
import com.dnd.accompany.domain.user.entity.enums.FoodPreference;
import com.dnd.accompany.domain.user.entity.enums.Gender;
import com.dnd.accompany.domain.user.entity.enums.Grade;
import com.dnd.accompany.domain.user.entity.enums.TravelPreference;
import com.dnd.accompany.domain.user.entity.enums.TravelStyle;

import java.util.List;

public record UserProfileResponse(
    Long userId,
    String nickname,
    String provider,
    String profileImageUrl,
    String description,
    Gender gender,
    int birthYear,
    String socialMediaUrl,
    Grade grade,
    List<TravelPreference> travelPreferences,
    List<TravelStyle> travelStyles,
    List<FoodPreference> foodPreferences
) {

    public static UserProfileResponse from(User user, UserProfile userProfile) {
        return new UserProfileResponse(
                user.getId(),
                user.getNickname(),
                user.getProvider(),
                user.getProfileImageUrl(),
                userProfile.getDescription(),
                userProfile.getGender(),
                userProfile.getBirthYear(),
                userProfile.getSocialMediaUrl(),
                userProfile.getGrade(),
                userProfile.getTravelPreferences(),
                userProfile.getTravelStyles(),
                userProfile.getFoodPreferences()
        );
    }
}
