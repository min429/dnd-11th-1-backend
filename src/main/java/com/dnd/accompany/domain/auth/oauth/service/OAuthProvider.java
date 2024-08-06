package com.dnd.accompany.domain.auth.oauth.service;

import java.util.Arrays;

import com.dnd.accompany.global.common.exception.NotFoundException;
import com.dnd.accompany.global.common.response.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthProvider {

	KAKAO("KAKAO");

	private final String name;

	public static OAuthProvider get(String name) {
		return Arrays.stream(OAuthProvider.values())
			.filter(provider -> provider.getName().equals(name))
			.findAny()
			.orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_PROVIDER));
	}
}
