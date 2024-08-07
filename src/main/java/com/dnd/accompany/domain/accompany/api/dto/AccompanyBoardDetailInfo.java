package com.dnd.accompany.domain.accompany.api.dto;

import java.time.LocalDateTime;

import com.dnd.accompany.domain.accompany.entity.enums.Category;
import com.dnd.accompany.domain.accompany.entity.enums.PreferredAge;
import com.dnd.accompany.domain.accompany.entity.enums.PreferredGender;
import com.dnd.accompany.domain.accompany.entity.enums.Region;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccompanyBoardDetailInfo {
	Long boardId;
	String title;
	String content;
	Region region;
	LocalDateTime startDate;
	LocalDateTime endDate;
	Long headCount;
	Long capacity;
	Category category;
	PreferredAge preferredAge;
	PreferredGender preferredGender;

	@Builder
	public AccompanyBoardDetailInfo(Long boardId, String title, String content, Region region, LocalDateTime startDate,
		LocalDateTime endDate, Long headCount, Long capacity, Category category, PreferredAge preferredAge,
		PreferredGender preferredGender) {
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.region = region;
		this.startDate = startDate;
		this.endDate = endDate;
		this.headCount = headCount;
		this.capacity = capacity;
		this.category = category;
		this.preferredAge = preferredAge;
		this.preferredGender = preferredGender;
	}
}