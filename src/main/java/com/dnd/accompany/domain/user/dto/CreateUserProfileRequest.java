package com.dnd.accompany.domain.user.dto;

import com.dnd.accompany.domain.user.entity.enums.FoodPreference;
import com.dnd.accompany.domain.user.entity.enums.Gender;
import com.dnd.accompany.domain.user.entity.enums.TravelPreference;
import com.dnd.accompany.domain.user.entity.enums.TravelStyle;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateUserProfileRequest(
    int birthYear,

    @NotNull
    Gender gender,

    @NotEmpty
    List<TravelPreference> travelPreferences,

    @NotEmpty
    List<TravelStyle> travelStyles,

    @NotEmpty
    List<FoodPreference> foodPreferences
) {
}
