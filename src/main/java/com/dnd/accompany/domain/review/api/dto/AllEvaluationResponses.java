package com.dnd.accompany.domain.review.api.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AllEvaluationResponses (
        List<EvaluationResponse> evaluationResponse,
        int evaluationCount
){
}
