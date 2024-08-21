package com.dnd.accompany.domain.accompany.api.dto;


import com.dnd.accompany.domain.user.dto.UserProfileDetailResponse;

public record ReadAccompanyResponse(
	AccompanyBoardThumbnail boardThumbnail,
	UserProfileDetailResponse profileInfo,
	AccompanyRequestDetailInfo requestInfo
) {
}
