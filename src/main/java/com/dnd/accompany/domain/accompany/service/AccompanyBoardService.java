package com.dnd.accompany.domain.accompany.service;

import static com.dnd.accompany.domain.accompany.entity.AccompanyBoard.*;
import static com.dnd.accompany.domain.accompany.entity.enums.Role.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.accompany.domain.accompany.api.dto.AccompanyBoardDetailInfo;
import com.dnd.accompany.domain.accompany.api.dto.AccompanyBoardInfo;
import com.dnd.accompany.domain.accompany.api.dto.CreateAccompanyBoardRequest;
import com.dnd.accompany.domain.accompany.api.dto.CreateAccompanyBoardResponse;
import com.dnd.accompany.domain.accompany.api.dto.FindDetailInfoResult;
import com.dnd.accompany.domain.accompany.api.dto.PageResponse;
import com.dnd.accompany.domain.accompany.api.dto.ReadAccompanyBoardResponse;
import com.dnd.accompany.domain.accompany.api.dto.UserProfileDetailInfo;
import com.dnd.accompany.domain.accompany.entity.AccompanyBoard;
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
	public PageResponse<AccompanyBoardInfo> readAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Slice<AccompanyBoardInfo> sliceResult = accompanyBoardRepository.findBoardInfos(pageable);

		return new PageResponse<>(sliceResult.hasNext(), sliceResult.getContent());
	}

	@Transactional(readOnly = true)
	public ReadAccompanyBoardResponse read(Long boardId) {
		FindDetailInfoResult detailInfo = accompanyBoardRepository.findDetailInfo(boardId)
			.orElseThrow(() -> new AccompanyBoardNotFoundException(ErrorCode.ACCOMPANY_BOARD_NOT_FOUND));

		AccompanyBoardDetailInfo accompanyBoardDetailInfo = getAccompanyBoardDetailInfo(
			detailInfo);

		UserProfileDetailInfo userProfileDetailInfo = getUserProfileDetailInfo(
			detailInfo);

		return new ReadAccompanyBoardResponse(accompanyBoardDetailInfo, userProfileDetailInfo);
	}

	@Transactional
	public void delete(Long userId, Long boardId) {
		if (accompanyUserService.isHostOfBoard(userId, boardId)) {
			accompanyBoardRepository.deleteById(boardId);
			accompanyImageService.deleteByBoardId(boardId);
			accompanyUserService.deleteByBoardId(boardId);
			accompanyTagService.deleteByBoardId(boardId);
			accompanyRequestService.deleteByBoardId(boardId);
		} else {
			throw new AccompanyBoardAccessDeniedException(ErrorCode.ACCOMPANY_BOARD_ACCESS_DENIED);
		}
	}

	private static UserProfileDetailInfo getUserProfileDetailInfo(FindDetailInfoResult detailInfo) {
		return UserProfileDetailInfo.builder()
			.nickname(detailInfo.nickname())
			.provider(detailInfo.provider())
			.gender(detailInfo.gender())
			.travelPreferences(detailInfo.travelPreferences())
			.travelStyles(detailInfo.travelStyles())
			.foodPreferences(detailInfo.foodPreferences())
			.build();
	}

	private static AccompanyBoardDetailInfo getAccompanyBoardDetailInfo(FindDetailInfoResult detailInfo) {
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
