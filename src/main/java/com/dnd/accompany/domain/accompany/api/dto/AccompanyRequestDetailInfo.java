package com.dnd.accompany.domain.accompany.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccompanyRequestDetailInfo {
	private Long boardId;
	private Long userId;
	private String introduce;
	private String chatLink;

	@Builder
	public AccompanyRequestDetailInfo(Long boardId, Long userId, String introduce, String chatLink) {
		this.boardId = boardId;
		this.userId = userId;
		this.introduce = introduce;
		this.chatLink = chatLink;
	}
}
