package com.dnd.accompany.domain.accompany.service;

import static com.dnd.accompany.domain.accompany.entity.enums.Role.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.accompany.domain.accompany.api.dto.AccompanyBoardDetailInfo;
import com.dnd.accompany.domain.accompany.api.dto.AccompanyBoardThumbnail;
import com.dnd.accompany.domain.accompany.api.dto.AccompanyRequestDetailInfo;
import com.dnd.accompany.domain.accompany.api.dto.CreateAccompanyBoardRequest;
import com.dnd.accompany.domain.accompany.api.dto.CreateAccompanyBoardResponse;
import com.dnd.accompany.domain.accompany.api.dto.FindBoardThumbnailResult;
import com.dnd.accompany.domain.accompany.api.dto.ReadAccompanyBoardResponse;
import com.dnd.accompany.domain.accompany.api.dto.ReadAccompanyResponse;
import com.dnd.accompany.domain.accompany.api.dto.UserProfileThumbnail;
import com.dnd.accompany.domain.accompany.entity.AccompanyBoard;
import com.dnd.accompany.domain.accompany.exception.accompanyboard.AccompanyBoardAccessDeniedException;
import com.dnd.accompany.domain.user.dto.UserProfileDetailResponse;
import com.dnd.accompany.domain.user.service.UserProfileService;
import com.dnd.accompany.global.common.response.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccompanyServiceFacade {

	private final AccompanyBoardService accompanyBoardService;
	private final AccompanyImageService accompanyImageService;
	private final AccompanyTagService accompanyTagService;
	private final AccompanyUserService accompanyUserService;
	private final AccompanyRequestService accompanyRequestService;
	private final UserProfileService userProfileService;

	@Transactional
	public CreateAccompanyBoardResponse createBoard(Long userId, CreateAccompanyBoardRequest request) {
		AccompanyBoard accompanyBoard = accompanyBoardService.save(request);

		accompanyImageService.save(accompanyBoard, request.imageUrls());
		accompanyTagService.save(accompanyBoard, request.tagNames());
		accompanyUserService.save(userId, accompanyBoard, HOST);

		return new CreateAccompanyBoardResponse(accompanyBoard.getId());
	}

	@Transactional(readOnly = true)
	public ReadAccompanyBoardResponse getBoardDetail(Long boardId) {
		AccompanyBoardDetailInfo boardDetailInfo = getBoardDetailInfo(boardId);
		UserProfileThumbnail profileThumbnail = accompanyUserService.getUserProfileThumbnail(boardId);

		return new ReadAccompanyBoardResponse(boardDetailInfo, profileThumbnail);
	}

	private AccompanyBoardDetailInfo getBoardDetailInfo(Long boardId) {
		AccompanyBoard accompanyBoard = accompanyBoardService.getAccompanyBoard(boardId);

		List<String> tagNames = accompanyTagService.getTagNames(boardId);
		List<String> imageUrls = accompanyImageService.getImageUrls(boardId);

		AccompanyBoardDetailInfo boardDetailInfo = new AccompanyBoardDetailInfo(accompanyBoard, tagNames, imageUrls);
		return boardDetailInfo;
	}

	@Transactional
	public void removeBoard(Long userId, Long boardId) {
		if (accompanyBoardService.isHostOfBoard(userId, boardId)) {
			accompanyBoardService.getById(boardId).remove();
		} else {
			throw new AccompanyBoardAccessDeniedException(ErrorCode.ACCOMPANY_BOARD_ACCESS_DENIED);
		}
	}

	@Transactional
	public void deleteBoard(Long userId, Long boardId) {
		if (accompanyBoardService.isHostOfBoard(userId, boardId)) {
			accompanyImageService.deleteByBoardId(boardId);
			accompanyTagService.deleteByBoardId(boardId);
			accompanyRequestService.deleteByBoardId(boardId);
			accompanyUserService.deleteByBoardId(boardId);
			accompanyBoardService.deleteByBoardId(boardId);
		} else {
			throw new AccompanyBoardAccessDeniedException(ErrorCode.ACCOMPANY_BOARD_ACCESS_DENIED);
		}
	}

	@Transactional(readOnly = true)
	public AccompanyBoardThumbnail getBoardThumbnail(Long boardId, Long userId) {
		FindBoardThumbnailResult result = accompanyBoardService.getBoardThumbnail(boardId);
		String nickname = accompanyUserService.getNickname(userId);
		List<String> imageUrls = accompanyImageService.getImageUrls(boardId);

		AccompanyBoardThumbnail boardThumbnail = AccompanyBoardThumbnail.builder()
			.boardId(result.boardId())
			.title(result.title())
			.region(result.region())
			.startDate(result.startDate())
			.endDate(result.endDate())
			.nickname(nickname)
			.imageUrls(imageUrls)
			.build();

		return boardThumbnail;
	}

	@Transactional(readOnly = true)
	public ReadAccompanyResponse getRequestDetail(Long requestId, Long userId) {
		Long boardId = accompanyRequestService.getBoardId(requestId);
		Long hostId = accompanyUserService.getHostIdByAccompanyBoardId(boardId);
		AccompanyBoardThumbnail boardThumbnail = getBoardThumbnail(boardId, hostId);

		Long applicantId = accompanyRequestService.getApplicantId(requestId);
		boolean isReceived = userId != applicantId;

		UserProfileDetailResponse profileDetailInfo;
		AccompanyRequestDetailInfo requestDetailInfo;

		if (isReceived) {
			profileDetailInfo = getUserProfileDetailInfo(applicantId);
		} else {
			profileDetailInfo = getUserProfileDetailInfo(hostId);
		}

		requestDetailInfo = accompanyRequestService.getRequestDetailInfo(boardId, applicantId);

		return new ReadAccompanyResponse(boardThumbnail, profileDetailInfo, requestDetailInfo);
	}

	private UserProfileDetailResponse getUserProfileDetailInfo(Long userId) {
		return userProfileService.findUserProfileDetails(userId);
	}
}
