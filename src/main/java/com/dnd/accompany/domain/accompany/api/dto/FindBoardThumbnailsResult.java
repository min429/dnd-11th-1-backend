package com.dnd.accompany.domain.accompany.api.dto;

import static java.util.Collections.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.dnd.accompany.domain.accompany.entity.enums.Region;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FindBoardThumbnailsResult extends FindSlicesResult{
	private final Long requestId;
	private final String title;
	private final Region region;
	private final LocalDateTime startDate;
	private final LocalDateTime endDate;
	private final String nickname;
	private final String imageUrls;

	public FindBoardThumbnailsResult(Long requestId, String title, Region region,
		LocalDateTime startDate, LocalDateTime endDate,
		String nickname, String imageUrls, String cursor) {
		super(cursor);

		this.requestId = requestId;
		this.title = title;
		this.region = region;
		this.startDate = startDate;
		this.endDate = endDate;
		this.nickname = nickname;
		this.imageUrls = imageUrls;
	}

	public List<String> getImageUrlsAsList() {
		if (imageUrls == null || imageUrls.isEmpty()) {
			return emptyList();
		}
		return Arrays.asList(imageUrls.split(","));
	}
}
