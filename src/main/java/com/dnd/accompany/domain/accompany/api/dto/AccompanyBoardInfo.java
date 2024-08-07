package com.dnd.accompany.domain.accompany.api.dto;

import java.time.LocalDateTime;

import com.dnd.accompany.domain.accompany.entity.enums.Region;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccompanyBoardInfo {
	private Long boardId;
	private String title;
	private Region region;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String nickname;

	@Builder
	public AccompanyBoardInfo(Long boardId, String title, Region region, LocalDateTime startDate, LocalDateTime endDate,
		String nickname) {
		this.boardId = boardId;
		this.title = title;
		this.region = region;
		this.startDate = startDate;
		this.endDate = endDate;
		this.nickname = nickname;
	}
}