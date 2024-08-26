package com.dnd.accompany.domain.review.api.dto;

import com.dnd.accompany.domain.review.entity.Personality;
import com.dnd.accompany.domain.review.entity.TravelPreference;
import com.dnd.accompany.domain.review.entity.TravelStyle;
import com.dnd.accompany.domain.review.entity.enums.CompanionType;
import com.dnd.accompany.domain.review.entity.enums.PersonalityType;
import com.dnd.accompany.domain.review.entity.enums.RecommendationStatus;
import com.dnd.accompany.domain.review.entity.enums.SatisfactionLevel;
import com.dnd.accompany.domain.review.entity.enums.TravelPreferenceType;
import com.dnd.accompany.domain.review.entity.enums.TravelStyleType;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateReviewRequest(
    @NotNull
    Long receiverId,

    @NotNull
    Long accompanyBoardId,

    @NotNull
    SatisfactionLevel satisfactionLevel,

    @NotNull
    RecommendationStatus recommendationStatus,

    @NotNull
    CompanionType companionType,

    @NotNull
    List<PersonalityType> personalityType,

    @NotNull
    List<TravelPreferenceType> travelPreference,

    @NotNull
    List<TravelStyleType> travelStyle,

    String detailContent,

    @NotNull
    List<String> reviewImageUrls
) {
}
