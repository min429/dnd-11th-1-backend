package com.dnd.accompany.domain.review.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleEvaluationResult {
    private List<TypeCountResult> travelStyles;
    private List<TypeCountResult> travelPreferences;
    private List<TypeCountResult> personalityTypes;
}