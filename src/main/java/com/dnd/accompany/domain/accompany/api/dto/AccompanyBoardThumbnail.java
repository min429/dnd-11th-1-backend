package com.dnd.accompany.domain.accompany.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.dnd.accompany.domain.accompany.entity.enums.Region;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccompanyBoardThumbnail {
	private Long boardId;
	private String title;
	private Region region;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String nickname;
	private List<String> imageUrls;

	@Builder
	public AccompanyBoardThumbnail(Long boardId, String title, Region region, LocalDateTime startDate,
		LocalDateTime endDate, String nickname, List<String> imageUrls) {
		this.boardId = boardId;
		this.title = title;
		this.region = region;
		this.startDate = startDate;
		this.endDate = endDate;
		this.nickname = nickname;
		this.imageUrls = imageUrls;
	}
}