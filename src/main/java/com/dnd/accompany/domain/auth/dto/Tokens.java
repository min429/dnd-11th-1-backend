package com.dnd.accompany.domain.auth.dto;

public record Tokens(
	String accessToken,
	String refreshToken
) {
}
