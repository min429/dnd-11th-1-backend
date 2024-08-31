package com.dnd.accompany.domain.review.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class EvaluationResponse {
    @JsonProperty("type")
    private String type;

    @JsonProperty("count")
    private long count;

    public static EvaluationResponse create(TypeCountResult topResult) {
        if (topResult != null) {
            return EvaluationResponse.builder()
                    .type(topResult.getType() != null ? topResult.getType() : null)
                    .count(topResult.getCount())
                    .build();
        } else {
            return EvaluationResponse.builder()
                    .type(null)
                    .count(0)
                    .build();
        }
    }
}


