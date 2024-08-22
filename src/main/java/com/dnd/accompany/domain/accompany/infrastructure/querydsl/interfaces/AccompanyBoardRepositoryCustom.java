package com.dnd.accompany.domain.accompany.infrastructure.querydsl.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.dnd.accompany.domain.accompany.api.dto.FindBoardThumbnailsResult;
import com.dnd.accompany.domain.accompany.entity.enums.Region;

public interface AccompanyBoardRepositoryCustom {
	Slice<FindBoardThumbnailsResult> findBoardThumbnails(Pageable pageable, Region region);

	Slice<FindBoardThumbnailsResult> findBoardThumbnailsByUserId(Pageable pageable, Long userId);

	boolean isHostOfBoard(Long userId, Long boardId);
}
