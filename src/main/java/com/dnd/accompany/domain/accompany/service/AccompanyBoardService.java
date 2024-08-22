package com.dnd.accompany.domain.accompany.service;

import static com.dnd.accompany.domain.accompany.entity.AccompanyBoard.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.accompany.domain.accompany.api.dto.AccompanyBoardThumbnail;
import com.dnd.accompany.domain.accompany.api.dto.CreateAccompanyBoardRequest;
import com.dnd.accompany.domain.accompany.api.dto.FindBoardThumbnailResult;
import com.dnd.accompany.domain.accompany.api.dto.FindBoardThumbnailsResult;
import com.dnd.accompany.domain.accompany.api.dto.PageResponse;
import com.dnd.accompany.domain.accompany.entity.AccompanyBoard;
import com.dnd.accompany.domain.accompany.entity.enums.Region;
import com.dnd.accompany.domain.accompany.exception.accompanyboard.AccompanyBoardNotFoundException;
import com.dnd.accompany.domain.accompany.infrastructure.AccompanyBoardRepository;
import com.dnd.accompany.domain.user.exception.UserNotFoundException;
import com.dnd.accompany.global.common.response.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccompanyBoardService {

	private final AccompanyBoardRepository accompanyBoardRepository;

	@Transactional
	public AccompanyBoard save(CreateAccompanyBoardRequest request) {
		return accompanyBoardRepository.save(
			builder()
				.title(request.title())
				.content(request.content())
				.region(request.region())
				.startDate(request.startDate())
				.endDate(request.endDate())
				.headCount(1L)
				.capacity(request.capacity())
				.categories(request.categories())
				.preferredAge(request.preferredAge())
				.preferredGender(request.preferredGender())
				.build()
		);
	}

	@Transactional(readOnly = true)
	public PageResponse<AccompanyBoardThumbnail> getAllBoards(Pageable pageable, Region region) {
		Slice<FindBoardThumbnailsResult> sliceResult = accompanyBoardRepository.findBoardThumbnails(pageable, region);

		List<AccompanyBoardThumbnail> thumbnails = getBoardThumbnails(sliceResult.getContent());

		return new PageResponse<>(sliceResult.hasNext(), thumbnails);
	}

	/**
	 * imageUrls의 타입을 String -> List<String>로 변환합니다.
	 */
	private List<AccompanyBoardThumbnail> getBoardThumbnails(List<FindBoardThumbnailsResult> results) {
		List<AccompanyBoardThumbnail> thumbnails = results.stream()
			.map(result -> AccompanyBoardThumbnail.builder()
				.boardId(result.requestId())
				.title(result.title())
				.region(result.region())
				.startDate(result.startDate())
				.endDate(result.endDate())
				.nickname(result.nickname())
				.imageUrls(result.getImageUrlsAsList())
				.build())
			.toList();
		return thumbnails;
	}

	@Transactional(readOnly = true)
	public AccompanyBoard getAccompanyBoard(Long boardId){
		return accompanyBoardRepository.findByIdWithCategories(boardId)
			.orElseThrow(() -> new AccompanyBoardNotFoundException(ErrorCode.ACCOMPANY_BOARD_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public boolean isHostOfBoard(Long userId, Long boardId){
		return accompanyBoardRepository.isHostOfBoard(userId, boardId);
	}

	@Transactional
	public void deleteByBoardId(Long boardId){
		accompanyBoardRepository.deleteById(boardId);
	}

	@Transactional(readOnly = true)
	public FindBoardThumbnailResult getBoardThumbnail(Long boardId){
		return accompanyBoardRepository.findBoardThumbnail(boardId)
			.orElseThrow(() -> new AccompanyBoardNotFoundException(ErrorCode.ACCOMPANY_BOARD_NOT_FOUND));
	}
}
