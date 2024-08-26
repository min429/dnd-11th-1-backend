package com.dnd.accompany.domain.review.api.dto;

import com.dnd.accompany.domain.accompany.entity.AccompanyBoard;
import com.dnd.accompany.domain.accompany.entity.enums.Region;
import com.dnd.accompany.domain.review.entity.Personality;
import com.dnd.accompany.domain.review.entity.Review;
import com.dnd.accompany.domain.review.entity.ReviewImage;
import com.dnd.accompany.domain.review.entity.TravelPreference;
import com.dnd.accompany.domain.review.entity.TravelStyle;
import com.dnd.accompany.domain.review.entity.enums.CompanionType;
import com.dnd.accompany.domain.review.entity.enums.PersonalityType;
import com.dnd.accompany.domain.review.entity.enums.TravelPreferenceType;
import com.dnd.accompany.domain.review.entity.enums.TravelStyleType;
import com.dnd.accompany.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDetailsResult {
    private String nickname;
    private Region region;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private CompanionType companionType;
    private List<PersonalityType> personalityType;
    private List<TravelPreferenceType> travelPreference;
    private List<TravelStyleType> travelStyle;
    private String detailContent;
    private List<String> imageUrl;

    public static ReviewDetailsResult of(User user, Review review, AccompanyBoard accompanyBoard) {
        return new ReviewDetailsResult(
                user.getNickname(),
                accompanyBoard.getRegion(),
                accompanyBoard.getStartDate(),
                accompanyBoard.getEndDate(),
                review.getCompanionType(),
                toPersonalityType(review.getPersonalityType()),
                toTravelPreferenceType(review.getTravelPreference()),
                toTravelStyleType(review.getTravelStyle()),
                review.getDetailContent(),
                toImageUrl(review.getReviewImageUrls())
        );
    }

    private static List<PersonalityType> toPersonalityType(List<Personality> personality) {
        return personality.stream()
                .map(Personality::getType)
                .toList();
    }

    private static List<String> toImageUrl(List<ReviewImage> reviewImages) {
        return reviewImages.stream()
                .map(ReviewImage::getImageUrl)
                .toList();
    }

    private static List<TravelPreferenceType> toTravelPreferenceType(List<TravelPreference> travelPreferences) {
        return travelPreferences.stream()
                .map(TravelPreference::getType)
                .toList();
    }

    private static List<TravelStyleType> toTravelStyleType(List<TravelStyle> travelStyles) {
        return travelStyles.stream()
                .map(TravelStyle::getType)
                .toList();
    }
}
