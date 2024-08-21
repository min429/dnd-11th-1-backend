package com.dnd.accompany.domain.accompany.infrastructure.querydsl.interfaces;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.dnd.accompany.domain.accompany.api.dto.FindBoardThumbnailsResult;
import com.dnd.accompany.domain.accompany.entity.AccompanyRequest;
import com.dnd.accompany.domain.accompany.entity.enums.Role;

public interface AccompanyRequestRepositoryCustom {
	Slice<FindBoardThumbnailsResult> findBoardThumbnails(Pageable pageable, Long applicantId);
}
