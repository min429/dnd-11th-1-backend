package com.dnd.accompany.domain.accompany.api.dto;

import com.dnd.accompany.domain.user.entity.enums.Gender;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileThumbnail {
	private Long userId;
	private String nickname;
	private String profileImageUrl;
	private int birthYear;
	private Gender gender;

	@Builder
	public UserProfileThumbnail(Long userId, String nickname, String profileImageUrl, int birthYear, Gender gender) {
		this.userId = userId;
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
		this.birthYear = birthYear;
		this.gender = gender;
	}
}
