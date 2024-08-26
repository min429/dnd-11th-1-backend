package com.dnd.accompany.domain.review.api.dto;

import com.dnd.accompany.domain.accompany.entity.enums.Region;
import com.dnd.accompany.domain.review.entity.enums.CompanionType;
import com.dnd.accompany.domain.review.entity.enums.PersonalityType;
import com.dnd.accompany.domain.review.entity.enums.TravelPreferenceType;
import com.dnd.accompany.domain.review.entity.enums.TravelStyleType;

import java.time.LocalDateTime;
import java.util.List;

public record ReviewDetailsResponse(
        String writerNickname,
        Region region,
        LocalDateTime startDate,
        LocalDateTime endDate,
        CompanionType companionType,
        List<PersonalityType>personalityType,
        List<TravelPreferenceType> travelPreference,
        List<TravelStyleType> travelStyle,
        String detailContent,
        List<String> reviewImageUrls
) {

    public static ReviewDetailsResponse of(ReviewDetailsResult result) {
        return new ReviewDetailsResponse(
                result.getNickname(),
                result.getRegion(),
                result.getStartDate(),
                result.getEndDate(),
                result.getCompanionType(),
                result.getPersonalityType(),
                result.getTravelPreference(),
                result.getTravelStyle(),
                result.getDetailContent(),
                result.getImageUrl()
        );
    }
}
