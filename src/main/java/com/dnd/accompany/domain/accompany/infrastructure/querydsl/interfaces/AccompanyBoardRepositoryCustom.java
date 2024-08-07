package com.dnd.accompany.domain.accompany.infrastructure.querydsl.interfaces;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.dnd.accompany.domain.accompany.api.dto.AccompanyBoardInfo;
import com.dnd.accompany.domain.accompany.api.dto.FindDetailInfoResult;

public interface AccompanyBoardRepositoryCustom {
	Slice<AccompanyBoardInfo> findBoardInfos(Pageable pageable);

	Optional<FindDetailInfoResult> findDetailInfo(Long boardId);

	boolean isHostOfBoard(Long userId, Long boardId);
}
