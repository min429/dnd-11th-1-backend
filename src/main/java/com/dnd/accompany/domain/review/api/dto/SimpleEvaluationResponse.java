package com.dnd.accompany.domain.review.api.dto;

import lombok.Builder;

@Builder
public record SimpleEvaluationResponse(
        String travelStyle,
        long travelStyleCount,

        String travelPreference,
        long travelPreferenceCount,

        String personalityType,
        long personalityTypeCount,

        int evaluationCount
) {
}
