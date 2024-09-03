package com.dnd.accompany.domain.review.api.dto;

import com.dnd.accompany.domain.accompany.entity.enums.Region;
import com.dnd.accompany.domain.user.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class SimpleReviewResponse {
    private Long reviewId;
    private String nickname;
    private String profileImageUrl;
    private int age;
    private Gender gender;
    private Region region;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String detailContent;
}
