package com.dnd.accompany.domain.accompany.api.dto;

public record ReadAccompanyBoardResponse(
	AccompanyBoardDetailInfo boardInfo,
	UserProfileDetailInfo profileInfo
) {
}
