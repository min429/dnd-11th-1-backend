package com.dnd.accompany.domain.review.api.dto;

import com.dnd.accompany.domain.review.entity.enums.PersonalityType;
import com.dnd.accompany.domain.review.entity.enums.TravelPreferenceType;
import com.dnd.accompany.domain.review.entity.enums.TravelStyleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleEvaluationResult {
    private TravelStyleType travelStyle;
    private TravelPreferenceType travelPreference;
    private PersonalityType personalityType;
}
