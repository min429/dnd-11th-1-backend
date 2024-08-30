package com.dnd.accompany.domain.accompany.api.dto;

import static java.util.Collections.*;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FindApplicantDetailsResult extends FindSlicesResult {
	private final Long requestId;
	private final Long userId;
	private final String nickname;
	private final String provider;
	private final String profileImageUrl;
	private final String imageUrls;

	public FindApplicantDetailsResult(Long requestId, Long userId, String nickname, String provider,
		String profileImageUrl, String imageUrls, String cursor) {
		super(cursor);

		this.requestId = requestId;
		this.userId = userId;
		this.nickname = nickname;
		this.provider = provider;
		this.profileImageUrl = profileImageUrl;
		this.imageUrls = imageUrls;
	}

	public List<String> getImageUrlsAsList() {
		if (imageUrls == null || imageUrls.isEmpty()) {
			return emptyList();
		}
		return Arrays.asList(imageUrls.split(","));
	}
}
