package com.dnd.accompany.domain.user.dto;

import com.dnd.accompany.domain.user.entity.enums.FoodPreference;
import com.dnd.accompany.domain.user.entity.enums.Gender;
import com.dnd.accompany.domain.user.entity.enums.TravelPreference;
import com.dnd.accompany.domain.user.entity.enums.TravelStyle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateUserProfileRequest(
        @NotBlank
        String nickname,
        String description,
        String profileImageUrl,

        @NotNull
        Integer birthYear,
        @NotNull
        Gender gender,

        List<TravelPreference> travelPreferences,
        List<TravelStyle> travelStyles,
        List<FoodPreference> foodPreferences,
        String socialMediaLink
) {
}
