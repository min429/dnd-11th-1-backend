package com.dnd.accompany.domain.review.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TypeCountResult {
    private String type;
    private long count;
}