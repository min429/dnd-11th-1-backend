package com.dnd.accompany.domain.review.api.dto;

import com.dnd.accompany.domain.accompany.entity.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleReviewResult {
    private String nickname;
    private String profileImageUrl;
    private Region region;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String detailContent;
}
