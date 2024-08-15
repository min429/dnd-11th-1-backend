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
import com.dnd.accompany.domain.accompany.api.dto.PageResponse;
import com.dnd.accompany.domain.accompany.api.dto.ReadAccompanyBoardResponse;
import com.dnd.accompany.domain.accompany.api.dto.UserProfileThumbnail;
import com.dnd.accompany.domain.accompany.entity.AccompanyBoard;
import com.dnd.accompany.domain.accompany.entity.enums.Region;
import com.dnd.accompany.domain.accompany.exception.AccompanyBoardAccessDeniedException;
import com.dnd.accompany.domain.accompany.exception.AccompanyBoardNotFoundException;
import com.dnd.accompany.domain.accompany.infrastructure.AccompanyBoardRepository;
import com.dnd.accompany.domain.user.exception.UserNotFoundException;
import com.dnd.accompany.domain.user.exception.UserProfileNotFoundException;
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
				.categories(request.categories())
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
	 * imageUrls의 타입을 String -> List<String>로 변환합니다.
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
		AccompanyBoardDetailInfo boardDetailInfo = getAccompanyBoardDetailInfo(boardId);
		UserProfileThumbnail profileThumbnail = getUserProfileThumbnail(boardId);

		return new ReadAccompanyBoardResponse(boardDetailInfo, profileThumbnail);
	}

	private UserProfileThumbnail getUserProfileThumbnail(Long boardId) {
		Long userId = accompanyUserService.findUserIdByAccompanyBoardId(boardId)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

		UserProfileThumbnail profileThumbnail = accompanyBoardRepository.findUserProfileThumbnail(userId)
			.orElseThrow(() -> new UserProfileNotFoundException(ErrorCode.PROFILE_NOT_FOUND));
		return profileThumbnail;
	}

	private AccompanyBoardDetailInfo getAccompanyBoardDetailInfo(Long boardId) {
		AccompanyBoard accompanyBoard = accompanyBoardRepository.findByIdWithCategories(boardId)
			.orElseThrow(() -> new AccompanyBoardNotFoundException(ErrorCode.ACCOMPANY_BOARD_NOT_FOUND));

		List<String> tagNames = accompanyTagService.findTagNamesByAccompanyBoardId(boardId);
		List<String> imageUrls = accompanyImageService.findImageUrlsByAccompanyBoardId(boardId);

		AccompanyBoardDetailInfo boardDetailInfo = new AccompanyBoardDetailInfo(accompanyBoard, tagNames, imageUrls);
		return boardDetailInfo;
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
}
