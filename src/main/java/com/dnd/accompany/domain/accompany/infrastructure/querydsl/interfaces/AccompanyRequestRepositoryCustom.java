package com.dnd.accompany.domain.accompany.infrastructure.querydsl.interfaces;

import java.util.Optional;

import org.springframework.data.domain.Slice;

import com.dnd.accompany.domain.accompany.api.dto.FindApplicantDetailsResult;
import com.dnd.accompany.domain.accompany.api.dto.FindBoardThumbnailsResult;
import com.dnd.accompany.domain.accompany.entity.AccompanyRequest;

public interface AccompanyRequestRepositoryCustom {
	Slice<FindBoardThumbnailsResult> findBoardThumbnails(String cursor, int size, Long applicantId);

	Slice<FindApplicantDetailsResult> findApplicantDetails(String cursor, int size, Long hostId);

	boolean existsBy(Long userId, Long boardId);
}
