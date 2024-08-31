package com.dnd.accompany.domain.accompany.infrastructure.querydsl.interfaces;

import org.springframework.data.domain.Slice;

import com.dnd.accompany.domain.accompany.api.dto.FindBoardThumbnailsResult;
import com.dnd.accompany.domain.accompany.entity.enums.Region;

public interface AccompanyBoardRepositoryCustom {
  
	Slice<FindBoardThumbnailsResult> findBoardThumbnails(String cursor, int size, Region region);

	Slice<FindBoardThumbnailsResult> findRecordsByUserId(String cursor, int size, Long userId);

	Slice<FindBoardThumbnailsResult> findBoardThumbnailsByHostId(String cursor, int size, Long userId);
  
	Slice<FindBoardThumbnailsResult> findBoardThumbnailsByKeyword(String cursor, int size, String keyword);

	boolean isHostOfBoard(Long userId, Long boardId);
}
