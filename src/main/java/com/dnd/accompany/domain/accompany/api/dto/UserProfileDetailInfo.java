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
	private int birthYear;
	private Gender gender;
	private List<TravelPreference> travelPreferences;
	private List<TravelStyle> travelStyles;
	private List<FoodPreference> foodPreferences;
	private List<String> userImageUrls;

	@Builder
	public UserProfileDetailInfo(String nickname, String provider, int birthYear, Gender gender,
		List<TravelPreference> travelPreferences,
		List<TravelStyle> travelStyles, List<FoodPreference> foodPreferences, List<String> userImageUrls) {
		this.nickname = nickname;
		this.provider = provider;
		this.birthYear = birthYear;
		this.gender = gender;
		this.travelPreferences = travelPreferences;
		this.travelStyles = travelStyles;
		this.foodPreferences = foodPreferences;
		this.userImageUrls = userImageUrls;
	}
}
