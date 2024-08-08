package com.dnd.accompany.domain.accompany.api.dto;

import java.util.List;

import com.dnd.accompany.domain.user.entity.enums.FoodPreference;
import com.dnd.accompany.domain.user.entity.enums.Gender;
import com.dnd.accompany.domain.user.entity.enums.TravelPreference;
import com.dnd.accompany.domain.user.entity.enums.TravelStyle;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileDetailInfo {
	private String nickname;
	private String provider;
	private Gender gender;
	private List<TravelPreference> travelPreferences;
	private List<TravelStyle> travelStyles;
	private List<FoodPreference> foodPreferences;
	// List<UserImages> 추가 예정

	@Builder
	public UserProfileDetailInfo(String nickname, String provider, Gender gender,
		List<TravelPreference> travelPreferences,
		List<TravelStyle> travelStyles, List<FoodPreference> foodPreferences) {
		this.nickname = nickname;
		this.provider = provider;
		this.gender = gender;
		this.travelPreferences = travelPreferences;
		this.travelStyles = travelStyles;
		this.foodPreferences = foodPreferences;
	}
}
