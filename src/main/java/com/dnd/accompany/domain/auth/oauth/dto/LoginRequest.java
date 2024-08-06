package com.dnd.accompany.domain.auth.oauth.dto;

public record LoginRequest(
	String provider,
	String accessToken
) {
}
