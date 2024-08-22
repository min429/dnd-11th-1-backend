package com.dnd.accompany.domain.accompany.api.dto;

import java.util.List;

import com.dnd.accompany.domain.user.entity.enums.FoodPreference;
import com.dnd.accompany.domain.user.entity.enums.Gender;
import com.dnd.accompany.domain.user.entity.enums.Grade;
import com.dnd.accompany.domain.user.entity.enums.TravelPreference;
import com.dnd.accompany.domain.user.entity.enums.TravelStyle;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceivedAccompany {
	private Long requestId;
	private Long userId;
	private String nickname;
	private String provider;
	private String profileImageUrl;
	private String description;
	private Gender gender;
	private int birthYear;
	private String socialMediaUrl;
	private Grade grade;
	private List<TravelPreference> travelPreferences;
	private List<TravelStyle> travelStyles;
	private List<FoodPreference> foodPreferences;
	private List<String> userImageUrl;

	@Builder
	public ReceivedAccompany(Long requestId, Long userId, String nickname, String provider, String profileImageUrl, String description,
		Gender gender, int birthYear, String socialMediaUrl, Grade grade, List<TravelPreference> travelPreferences,
		List<TravelStyle> travelStyles, List<FoodPreference> foodPreferences, List<String> userImageUrl) {
		this.requestId = requestId;
		this.userId = userId;
        this.nickname = nickname;
        this.provider = provider;
        this.profileImageUrl = profileImageUrl;
		this.description = description;
		this.gender = gender;
		this.birthYear = birthYear;
		this.socialMediaUrl = socialMediaUrl;
		this.grade = grade;
		this.travelPreferences = travelPreferences;
		this.travelStyles = travelStyles;
		this.foodPreferences = foodPreferences;
		this.userImageUrl = userImageUrl;
	}
}
