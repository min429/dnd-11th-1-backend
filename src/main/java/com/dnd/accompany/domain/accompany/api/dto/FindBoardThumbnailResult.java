package com.dnd.accompany.domain.accompany.api.dto;

import java.time.LocalDateTime;

import com.dnd.accompany.domain.accompany.entity.enums.Region;

public record FindBoardThumbnailResult(
	Long boardId,
	String title,
	Region region,
	LocalDateTime startDate,
	LocalDateTime endDate
) {
}
