package com.dnd.accompany.domain.accompany.api.dto;

import static java.util.Collections.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.dnd.accompany.domain.accompany.entity.enums.Region;

public record FindBoardThumbnailsResult(
	Long boardId,
	String title,
	Region region,
	LocalDateTime startDate,
	LocalDateTime endDate,
	String nickname,
	String imageUrls
) {
	public List<String> getImageUrlsAsList() {
		if (imageUrls == null || imageUrls.isEmpty()) {
			return emptyList();
		}
		return Arrays.asList(imageUrls.split(","));
	}
}
