package com.dnd.accompany.domain.accompany.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileDetailInfo {
	private String nickname;

	@Builder
	public UserProfileDetailInfo(String nickname) {
		this.nickname = nickname;
	}
}
