package com.dnd.accompany.domain.review.api.dto;

import com.dnd.accompany.domain.review.entity.enums.PersonalityType;
import com.dnd.accompany.domain.review.entity.enums.TravelPreferenceType;
import com.dnd.accompany.domain.review.entity.enums.TravelStyleType;
import lombok.Builder;

@Builder
public record SimpleEvaluationResponse(
        TravelStyleType travelStyle,
        TravelPreferenceType travelPreference,
        PersonalityType personalityType,
        int evaluationCount
) {
}
