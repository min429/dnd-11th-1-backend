package com.dnd.accompany.domain.accompany.service;

import static com.dnd.accompany.domain.accompany.api.dto.FindBoardThumbnailsResult.*;
import static com.dnd.accompany.domain.accompany.entity.AccompanyBoard.*;

import java.util.List;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.accompany.domain.accompany.api.dto.AccompanyBoardThumbnail;
import com.dnd.accompany.domain.accompany.api.dto.CreateAccompanyBoardRequest;
import com.dnd.accompany.domain.accompany.api.dto.FindBoardThumbnailResult;
import com.dnd.accompany.domain.accompany.api.dto.FindBoardThumbnailsResult;
import com.dnd.accompany.domain.accompany.api.dto.PageRequest;
import com.dnd.accompany.domain.accompany.api.dto.PageResponse;
import com.dnd.accompany.domain.accompany.entity.AccompanyBoard;
import com.dnd.accompany.domain.accompany.entity.enums.Region;
import com.dnd.accompany.domain.accompany.exception.accompanyboard.AccompanyBoardNotFoundException;
import com.dnd.accompany.domain.accompany.infrastructure.AccompanyBoardRepository;
import com.dnd.accompany.global.common.response.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccompanyBoardService {

	private final AccompanyBoardRepository accompanyBoardRepository;

	@Transactional
	public AccompanyBoard save(CreateAccompanyBoardRequest request) {
		return accompanyBoardRepository.save(
			AccompanyBoard.builder()
				.title(request.title())
				.content(request.content())
				.region(request.region())
				.startDate(request.startDate())
				.endDate(request.endDate())
				.headCount(1L)
				.capacity(request.capacity())
				.boardStatus(request.boardStatus())
				.categories(request.categories())
				.preferredAge(request.preferredAge())
				.preferredGender(request.preferredGender())
				.build()
		);
	}

	@Transactional(readOnly = true)
	public PageResponse<AccompanyBoardThumbnail> getMyBoards(PageRequest request, Long userId) {
		Slice<FindBoardThumbnailsResult> sliceResult = accompanyBoardRepository.findBoardThumbnailsByHostId(request.cursor(), request.size(), userId);

		List<AccompanyBoardThumbnail> thumbnails = getBoardThumbnails(sliceResult.getContent());

		return new PageResponse<>(sliceResult.hasNext(), thumbnails, getLastCursor(sliceResult.getContent()));
	}

  public PageResponse<AccompanyBoardThumbnail> getMatchedBoards(PageRequest request, String keyword) {
		Slice<FindBoardThumbnailsResult> sliceResult = accompanyBoardRepository.findBoardThumbnailsByKeyword(request.cursor(), request.size(), keyword);

		List<AccompanyBoardThumbnail> thumbnails = getBoardThumbnails(sliceResult.getContent());

		return new PageResponse<>(sliceResult.hasNext(), thumbnails, getLastCursor(sliceResult.getContent()));
	}

	@Transactional(readOnly = true)
	public PageResponse<AccompanyBoardThumbnail> getAllBoards(PageRequest request, Region region) {
		Slice<FindBoardThumbnailsResult> sliceResult = accompanyBoardRepository.findBoardThumbnails(request.cursor(), request.size(), region);

		List<AccompanyBoardThumbnail> thumbnails = getBoardThumbnails(sliceResult.getContent());

		return new PageResponse<>(sliceResult.hasNext(), thumbnails, getLastCursor(sliceResult.getContent()));
	}

	@Transactional(readOnly = true)
	public PageResponse<AccompanyBoardThumbnail> getAllRecords(PageRequest request, Long userId) {
		Slice<FindBoardThumbnailsResult> sliceResult = accompanyBoardRepository.findRecordsByUserId(request.cursor(), request.size(), userId);

		List<AccompanyBoardThumbnail> thumbnails = getBoardThumbnails(sliceResult.getContent());

		return new PageResponse<>(sliceResult.hasNext(), thumbnails, getLastCursor(sliceResult.getContent()));
	}


	/**
	 * imageUrls의 타입을 String -> List<String>로 변환합니다.
	 */
	private List<AccompanyBoardThumbnail> getBoardThumbnails(List<FindBoardThumbnailsResult> results) {
		List<AccompanyBoardThumbnail> thumbnails = results.stream()
			.map(result -> AccompanyBoardThumbnail.builder()
				.boardId(result.getRequestId())
				.title(result.getTitle())
				.region(result.getRegion())
				.startDate(result.getStartDate())
				.endDate(result.getEndDate())
				.nickname(result.getNickname())
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

	@Transactional(readOnly = true)
	public AccompanyBoard getById(Long boardId){
		return accompanyBoardRepository.findById(boardId)
			.orElseThrow(() -> new AccompanyBoardNotFoundException(ErrorCode.ACCOMPANY_BOARD_NOT_FOUND));
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
