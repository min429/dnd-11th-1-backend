package com.dnd.accompany.domain.accompany.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.dnd.accompany.domain.accompany.entity.enums.Region;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendedAccompany {
	private Long requestId;
	private String title;
	private Region region;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String nickname;
	private List<String> imageUrls;

	@Builder
	public SendedAccompany(Long requestId, String title, Region region, LocalDateTime startDate, LocalDateTime endDate,
        String nickname, List<String> imageUrls) {
		this.requestId = requestId;
		this.title = title;
		this.region = region;
		this.startDate = startDate;
		this.endDate = endDate;
		this.nickname = nickname;
		this.imageUrls = imageUrls;
	}
}
