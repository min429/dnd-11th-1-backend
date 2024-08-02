package com.dnd.accompany.domain.auth.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthUserDataRequest {
	private String accessToken;
}
