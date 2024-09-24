package com.dnd.accompany.domain.accompany.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccompanyRequestDetailInfo {
	private Long requestId;
	private Long userId;
	private String introduce;
	private String chatLink;
	private boolean isReceived;

	@Builder
	public AccompanyRequestDetailInfo(Long requestId, Long userId, String introduce, String chatLink, boolean isReceived) {
		this.requestId = requestId;
		this.userId = userId;
		this.introduce = introduce;
		this.chatLink = chatLink;
		this.isReceived = isReceived;
	}
}
