package com.dnd.accompany.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.dnd.accompany.domain.user.entity.enums.FoodPreference;
import com.dnd.accompany.domain.user.entity.enums.Gender;
import com.dnd.accompany.domain.user.entity.enums.Grade;
import com.dnd.accompany.domain.user.entity.enums.TravelPreference;
import com.dnd.accompany.domain.user.entity.enums.TravelStyle;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.dnd.accompany.domain.user.entity.enums.Grade.ROOKIE;

@Table(name = "user_profiles")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE user_profiles SET deleted = true WHERE id = ?")
public class UserProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long userId;

  @Column(length = 50)
  private String description;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Column(nullable = false)
  private int birthYear;

  @Column
  private String socialMediaUrl;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Grade grade = ROOKIE;

  @Builder.Default
  @ElementCollection(targetClass = TravelPreference.class, fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private List<TravelPreference> travelPreferences = new ArrayList<>();

  @Builder.Default
  @ElementCollection(targetClass = TravelStyle.class, fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private List<TravelStyle> travelStyles = new ArrayList<>();

  @Builder.Default
  @ElementCollection(targetClass = FoodPreference.class, fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private List<FoodPreference> foodPreferences = new ArrayList<>();

  private boolean deleted = Boolean.FALSE;

  public void updateUserProfile(
          String description,
          Gender gender,
          int birthYear,
          List<TravelPreference> travelPreferences,
          List<TravelStyle> travelStyles,
          List<FoodPreference> foodPreferences,
          String socialMediaUrl
  ) {
      this.description = description;
      this.gender = gender;
      this.birthYear = birthYear;
      this.travelPreferences = travelPreferences;
      this.travelStyles = travelStyles;
      this.foodPreferences = foodPreferences;
      this.socialMediaUrl = socialMediaUrl;
  }
}
