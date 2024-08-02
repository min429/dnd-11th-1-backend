package com.dnd.accompany.domain.auth.oauth.dto;

import com.dnd.accompany.domain.auth.oauth.service.OAuthProvider;

public record LoginRequest(
	OAuthProvider provider,
	String accessToken
) {
}
