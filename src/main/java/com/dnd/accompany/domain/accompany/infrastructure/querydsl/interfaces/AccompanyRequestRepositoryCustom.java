package com.dnd.accompany.domain.accompany.infrastructure.querydsl.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.dnd.accompany.domain.accompany.api.dto.FindBoardThumbnailsResult;

public interface AccompanyRequestRepositoryCustom {
	Slice<FindBoardThumbnailsResult> findBoardThumbnails(Pageable pageable, Long applicantId);
}
