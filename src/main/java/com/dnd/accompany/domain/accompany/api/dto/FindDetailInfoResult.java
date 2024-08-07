package com.dnd.accompany.domain.accompany.api.dto;

import java.time.LocalDateTime;

import com.dnd.accompany.domain.accompany.entity.enums.Category;
import com.dnd.accompany.domain.accompany.entity.enums.PreferredAge;
import com.dnd.accompany.domain.accompany.entity.enums.PreferredGender;
import com.dnd.accompany.domain.accompany.entity.enums.Region;

public record FindDetailInfoResult(
	Long boardId,
	String title,
	String content,
	Region region,
	LocalDateTime startDate,
	LocalDateTime endDate,
	Long headCount,
	Long capacity,
	Category category,
	PreferredAge preferredAge,
	PreferredGender preferredGender,
	String nickname
) {
}
