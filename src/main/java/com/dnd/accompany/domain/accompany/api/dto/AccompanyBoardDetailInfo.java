package com.dnd.accompany.domain.accompany.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.dnd.accompany.domain.accompany.entity.AccompanyBoard;
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
	List<String> tagNames;
	List<String> imageUrls;
	Region region;
	LocalDateTime startDate;
	LocalDateTime endDate;
	Long headCount;
	Long capacity;
	List<Category> categories;
	PreferredAge preferredAge;
	PreferredGender preferredGender;

	@Builder
	public AccompanyBoardDetailInfo(Long boardId, String title, String content, Region region, LocalDateTime startDate,
		LocalDateTime endDate, Long headCount, Long capacity, PreferredAge preferredAge,
		PreferredGender preferredGender) {
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.region = region;
		this.startDate = startDate;
		this.endDate = endDate;
		this.headCount = headCount;
		this.capacity = capacity;
		this.preferredAge = preferredAge;
		this.preferredGender = preferredGender;
	}

	public AccompanyBoardDetailInfo(AccompanyBoard accompanyBoard, List<String> tagNames, List<String> imageUrls) {
		this.boardId = accompanyBoard.getId();
		this.title = accompanyBoard.getTitle();
		this.content = accompanyBoard.getContent();
		this.tagNames = tagNames;
		this.imageUrls = imageUrls;
		this.region = accompanyBoard.getRegion();
		this.startDate = accompanyBoard.getStartDate();
		this.endDate = accompanyBoard.getEndDate();
		this.headCount = accompanyBoard.getHeadCount();
		this.capacity = accompanyBoard.getCapacity();
		this.categories = accompanyBoard.getCategories();
		this.preferredAge = accompanyBoard.getPreferredAge();
		this.preferredGender = accompanyBoard.getPreferredGender();
	}
}