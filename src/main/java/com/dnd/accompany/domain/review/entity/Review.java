package com.dnd.accompany.domain.review.entity;

import com.dnd.accompany.domain.common.entity.TimeBaseEntity;
import com.dnd.accompany.domain.review.entity.enums.CompanionType;
import com.dnd.accompany.domain.review.entity.enums.PersonalityType;
import com.dnd.accompany.domain.review.entity.enums.RecommendationStatus;
import com.dnd.accompany.domain.review.entity.enums.SatisfactionLevel;
import com.dnd.accompany.domain.review.entity.enums.TravelPreferenceType;
import com.dnd.accompany.domain.review.entity.enums.TravelStyleType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reviews")
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE reviews SET deleted = true WHERE id = ?")
public class Review extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long receiverId;

    @Column(nullable = false)
    private Long writerId;

    @Column(nullable = false)
    private Long accompanyBoardId;

    @Column(length = 500)
    private String detailContent;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SatisfactionLevel satisfactionLevel;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RecommendationStatus recommendationStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CompanionType companionType;

    @OneToMany(fetch = LAZY, mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Personality> personalityType = new ArrayList<>();

    @OneToMany(fetch = LAZY, mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelPreference> travelPreference = new ArrayList<>();

    @OneToMany(fetch = LAZY, mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelStyle> travelStyle = new ArrayList<>();

    @OneToMany(fetch = LAZY, mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> reviewImageUrls = new ArrayList<>();

    private boolean deleted = Boolean.FALSE;

    public static Review createReview(Long writerId, Long receiverId, Long accompanyBoardId,
                                      SatisfactionLevel satisfactionLevel, RecommendationStatus recommendationStatus,
                                      CompanionType companionType, List<PersonalityType> personalityTypes,
                                      List<TravelPreferenceType> travelPreferences, List<TravelStyleType> travelStyles,
                                      String detailContent, List<String> reviewImageUrls) {
        Review review = Review.builder()
                .writerId(writerId)
                .receiverId(receiverId)
                .accompanyBoardId(accompanyBoardId)
                .satisfactionLevel(satisfactionLevel)
                .recommendationStatus(recommendationStatus)
                .companionType(companionType)
                .detailContent(detailContent)
                .build();

        review.addPersonalityTypes(personalityTypes);
        review.addTravelPreferences(travelPreferences);
        review.addTravelStyles(travelStyles);
        review.addReviewImages(reviewImageUrls);

        return review;
    }

    private void addPersonalityTypes(List<PersonalityType> personalityTypes) {
        List<Personality> personalities = personalityTypes.stream()
                .map(type -> Personality.builder()
                        .type(type)
                        .review(this)
                        .build())
                .toList();

        this.personalityType.addAll(personalities);
    }

    private void addTravelPreferences(List<TravelPreferenceType> travelPreferences) {
        List<TravelPreference> preferences = travelPreferences.stream()
                .map(type -> TravelPreference.builder()
                        .type(type)
                        .review(this)
                        .build())
                .toList();

        this.travelPreference.addAll(preferences);
    }

    private void addTravelStyles(List<TravelStyleType> travelStyles) {
        List<TravelStyle> styles = travelStyles.stream()
                .map(type -> TravelStyle.builder()
                        .type(type)
                        .review(this)
                        .build())
                .toList();

        this.travelStyle.addAll(styles);
    }

    private void addReviewImages(List<String> reviewImageUrls) {
        List<ReviewImage> images = reviewImageUrls.stream()
                .map(url -> ReviewImage.builder()
                        .imageUrl(url)
                        .review(this)
                        .build())
                .toList();

        this.reviewImageUrls.addAll(images);
    }
}
