package com.dnd.accompany.domain.review.api.dto;

import com.dnd.accompany.domain.accompany.entity.enums.Region;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SimpleReviewResponse(
        String nickname,
        String profileImageUrl,
        Region region,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String detailContent
) {
}
