package com.dnd.accompany.domain.accompany.service;

import static com.dnd.accompany.domain.accompany.entity.AccompanyBoard.*;
import static com.dnd.accompany.domain.accompany.entity.enums.Role.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.accompany.domain.accompany.api.dto.AccompanyBoardDetailInfo;
import com.dnd.accompany.domain.accompany.api.dto.AccompanyBoardThumbnail;
import com.dnd.accompany.domain.accompany.api.dto.CreateAccompanyBoardRequest;
import com.dnd.accompany.domain.accompany.api.dto.CreateAccompanyBoardResponse;
import com.dnd.accompany.domain.accompany.api.dto.FindBoardThumbnailsResult;
import com.dnd.accompany.domain.accompany.api.dto.FindDetailInfoResult;
import com.dnd.accompany.domain.accompany.api.dto.PageResponse;
import com.dnd.accompany.domain.accompany.api.dto.ReadAccompanyBoardResponse;
import com.dnd.accompany.domain.accompany.api.dto.UserProfileDetailInfo;
import com.dnd.accompany.domain.accompany.entity.AccompanyBoard;
import com.dnd.accompany.domain.accompany.entity.enums.Region;
import com.dnd.accompany.domain.accompany.exception.AccompanyBoardAccessDeniedException;
import com.dnd.accompany.domain.accompany.exception.AccompanyBoardNotFoundException;
import com.dnd.accompany.domain.accompany.infrastructure.AccompanyBoardRepository;
import com.dnd.accompany.global.common.response.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccompanyBoardService {

	private final AccompanyBoardRepository accompanyBoardRepository;
	private final AccompanyImageService accompanyImageService;
	private final AccompanyTagService accompanyTagService;
	private final AccompanyUserService accompanyUserService;
	private final AccompanyRequestService accompanyRequestService;

	@Transactional
	public CreateAccompanyBoardResponse create(Long userId, CreateAccompanyBoardRequest request) {
		AccompanyBoard accompanyBoard = accompanyBoardRepository.save(
			builder()
				.title(request.title())
				.content(request.content())
				.region(request.region())
				.startDate(request.startDate())
				.endDate(request.endDate())
				.headCount(1L)
				.capacity(request.capacity())
				.category(request.category())
				.preferredAge(request.preferredAge())
				.preferredGender(request.preferredGender())
				.build()
		);
		accompanyImageService.save(accompanyBoard, request.imageUrls());
		accompanyTagService.save(accompanyBoard, request.tagNames());
		accompanyUserService.save(userId, accompanyBoard, HOST);

		return new CreateAccompanyBoardResponse(accompanyBoard.getId());
	}

	@Transactional(readOnly = true)
	public PageResponse<AccompanyBoardThumbnail> readAll(Pageable pageable, Region region) {
		Slice<FindBoardThumbnailsResult> sliceResult = accompanyBoardRepository.findBoardThumbnails(pageable, region);

		List<AccompanyBoardThumbnail> thumbnails = getAccompanyBoardThumbnails(sliceResult.getContent());

		return new PageResponse<>(sliceResult.hasNext(), thumbnails);
	}

	/**
	 * imageUrls 타입을 String -> list<String>로 변환합니다.
	 */
	private static List<AccompanyBoardThumbnail> getAccompanyBoardThumbnails(List<FindBoardThumbnailsResult> results) {
		List<AccompanyBoardThumbnail> thumbnails = results.stream()
			.map(result -> AccompanyBoardThumbnail.builder()
				.boardId(result.boardId())
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
	public ReadAccompanyBoardResponse read(Long boardId) {
		FindDetailInfoResult detailInfo = accompanyBoardRepository.findDetailInfo(boardId)
			.orElseThrow(() -> new AccompanyBoardNotFoundException(ErrorCode.ACCOMPANY_BOARD_NOT_FOUND));

		AccompanyBoardDetailInfo accompanyBoardDetailInfo = getAccompanyBoardDetailInfo(detailInfo);
		UserProfileDetailInfo userProfileDetailInfo = getUserProfileDetailInfo(detailInfo);

		return new ReadAccompanyBoardResponse(accompanyBoardDetailInfo, userProfileDetailInfo);
	}

	@Transactional
	public void delete(Long userId, Long boardId) {
		if (accompanyUserService.isHostOfBoard(userId, boardId)) {
			accompanyImageService.deleteByBoardId(boardId);
			accompanyTagService.deleteByBoardId(boardId);
			accompanyRequestService.deleteByBoardId(boardId);
			accompanyUserService.deleteByBoardId(boardId);
			accompanyBoardRepository.deleteById(boardId);
		} else {
			throw new AccompanyBoardAccessDeniedException(ErrorCode.ACCOMPANY_BOARD_ACCESS_DENIED);
		}
	}

	private UserProfileDetailInfo getUserProfileDetailInfo(FindDetailInfoResult detailInfo) {
		return UserProfileDetailInfo.builder()
			.nickname(detailInfo.nickname())
			.provider(detailInfo.provider())
			.birthYear(detailInfo.birthYear())
			.gender(detailInfo.gender())
			.travelPreferences(detailInfo.travelPreferences())
			.travelStyles(detailInfo.travelStyles())
			.foodPreferences(detailInfo.foodPreferences())
			.build();
	}

	private AccompanyBoardDetailInfo getAccompanyBoardDetailInfo(FindDetailInfoResult detailInfo) {
		return AccompanyBoardDetailInfo.builder()
			.boardId(detailInfo.boardId())
			.title(detailInfo.title())
			.content(detailInfo.content())
			.region(detailInfo.region())
			.startDate(detailInfo.startDate())
			.endDate(detailInfo.endDate())
			.headCount(detailInfo.headCount())
			.capacity(detailInfo.capacity())
			.category(detailInfo.category())
			.preferredAge(detailInfo.preferredAge())
			.preferredGender(detailInfo.preferredGender())
			.build();
	}
}
