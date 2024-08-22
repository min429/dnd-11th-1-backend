package com.dnd.accompany.domain.accompany.api.dto;

import static java.util.Collections.*;

import java.util.Arrays;
import java.util.List;

public record FindApplicantDetailsResult(
	Long requestId,
	Long userId,
	String nickname,
	String provider,
	String profileImageUrl,
	String imageUrls
) {
	public List<String> getImageUrlsAsList() {
		if (imageUrls == null || imageUrls.isEmpty()) {
			return emptyList();
		}
		return Arrays.asList(imageUrls.split(","));
	}
}
