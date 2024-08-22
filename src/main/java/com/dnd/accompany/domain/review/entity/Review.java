package com.dnd.accompany.domain.review.entity;

import com.dnd.accompany.domain.common.entity.TimeBaseEntity;
import com.dnd.accompany.domain.review.entity.enums.CompanionType;
import com.dnd.accompany.domain.review.entity.enums.PersonalityType;
import com.dnd.accompany.domain.review.entity.enums.RecommendationStatus;
import com.dnd.accompany.domain.review.entity.enums.SatisfactionLevel;
import com.dnd.accompany.domain.review.entity.enums.TravelPreference;
import com.dnd.accompany.domain.review.entity.enums.TravelStyle;
import com.dnd.accompany.domain.user.entity.enums.FoodPreference;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Long writerId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long accompanyBoardId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SatisfactionLevel satisfactionLevel;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RecommendationStatus recommendationStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CompanionType companionType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = PersonalityType.class)
    private List<PersonalityType> personalityType = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = TravelPreference.class)
    private List<TravelPreference> travelPreference = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = TravelStyle.class)
    private List<TravelStyle> travelStyle = new ArrayList<>();

    @Column(length = 500)
    private String detailContent;

    @Column(length = 1000)
    private String reviewImageUrl;

    private boolean deleted = Boolean.FALSE;
}
