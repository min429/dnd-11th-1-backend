package com.dnd.accompany.domain.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserData {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	@Getter
	@NoArgsConstructor
	static class KakaoAccount {
		private KakaoProfile profile;
	}

	@Getter
	@NoArgsConstructor
	static class KakaoProfile {
		private String nickname;

		@JsonProperty("thumbnail_image_url")
		private String profileImage;
	}

	public String getNickname() {
		return kakaoAccount.getProfile().getNickname();
	}

	public String getProfileImage() {
		return kakaoAccount.getProfile().getProfileImage();
	}
}
