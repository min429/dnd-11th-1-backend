package com.dnd.accompany.domain.accompany.service;

import static com.dnd.accompany.domain.accompany.api.dto.FindApplicantDetailsResult.*;
import static com.dnd.accompany.domain.accompany.entity.enums.RequestState.*;
import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.accompany.domain.accompany.api.dto.AccompanyRequestDetailInfo;
import com.dnd.accompany.domain.accompany.api.dto.CreateAccompanyRequest;
import com.dnd.accompany.domain.accompany.api.dto.FindBoardThumbnailsResult;
import com.dnd.accompany.domain.accompany.api.dto.FindApplicantDetailsResult;
import com.dnd.accompany.domain.accompany.api.dto.FindSlicesResult;
import com.dnd.accompany.domain.accompany.api.dto.PageRequest;
import com.dnd.accompany.domain.accompany.api.dto.PageResponse;
import com.dnd.accompany.domain.accompany.api.dto.ReceivedAccompany;
import com.dnd.accompany.domain.accompany.api.dto.SendedAccompany;
import com.dnd.accompany.domain.accompany.entity.AccompanyBoard;
import com.dnd.accompany.domain.accompany.entity.AccompanyRequest;
import com.dnd.accompany.domain.accompany.exception.accompanyboard.AccompanyBoardNotFoundException;
import com.dnd.accompany.domain.accompany.exception.accompanyrequest.AccompanyRequestNotFoundException;
import com.dnd.accompany.domain.accompany.infrastructure.AccompanyRequestRepository;
import com.dnd.accompany.domain.user.entity.User;
import com.dnd.accompany.domain.user.entity.UserProfile;
import com.dnd.accompany.domain.user.exception.UserNotFoundException;
import com.dnd.accompany.domain.user.infrastructure.UserProfileRepository;
import com.dnd.accompany.global.common.exception.BadRequestException;
import com.dnd.accompany.global.common.response.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccompanyRequestService {
	private final AccompanyRequestRepository accompanyRequestRepository;
	private final UserProfileRepository userProfileRepository;

	@Transactional
	public void save(Long userId, CreateAccompanyRequest request) {
		accompanyRequestRepository.save(AccompanyRequest.builder()
			.user(User.builder().id(userId).build())
			.accompanyBoard(getAccompanyBoard(request.boardId()))
			.requestState(HOLDING)
			.introduce(request.introduce())
			.chatLink(request.chatLink())
			.build());
	}

	@Transactional(readOnly = true)
	public PageResponse<ReceivedAccompany> getAllReceivedAccompanies(PageRequest request, Long hostId){
		Slice<FindApplicantDetailsResult> sliceResult = accompanyRequestRepository.findApplicantDetails(request.cursor(), request.size(), hostId);

		Set<Long> userIds = getUserIds(sliceResult);
		Map<Long, UserProfile> userProfileMap = getUserProfileMap(userIds);

		List<ReceivedAccompany> receivedAccompanies = getReceivedAccompanies(sliceResult.getContent(), userProfileMap);

		return new PageResponse<>(sliceResult.hasNext(), receivedAccompanies, FindSlicesResult.getLastCursor(sliceResult.getContent()));
	}

	private Set<Long> getUserIds(Slice<FindApplicantDetailsResult> results) {
		return results.getContent().stream()
			.map(result -> result.getUserId())
			.collect(toSet());
	}

	private Map<Long, UserProfile> getUserProfileMap(Set<Long> userIds) {
		return userProfileRepository.findByUserIdIn(userIds).stream()
			.collect(Collectors.toMap(UserProfile::getUserId, userProfile -> userProfile));
	}

	/**
	 * imageUrls의 타입을 String -> List<String>로 변환합니다.
	 */
	private static List<ReceivedAccompany> getReceivedAccompanies(List<FindApplicantDetailsResult> results, Map<Long, UserProfile> userProfileMap) {
		return results.stream()
			.map(result -> {
				UserProfile userProfile = userProfileMap.get(result.getUserId());

				return ReceivedAccompany.builder()
				.requestId(result.getRequestId())
				.userId(result.getUserId())
				.nickname(result.getNickname())
				.provider(result.getProvider())
				.profileImageUrl(result.getProfileImageUrl())
				.description(userProfile.getDescription())
				.gender(userProfile.getGender())
				.birthYear(userProfile.getBirthYear())
				.socialMediaUrl(userProfile.getSocialMediaUrl())
				.grade(userProfile.getGrade())
				.travelPreferences(userProfile.getTravelPreferences())
				.travelStyles(userProfile.getTravelStyles())
				.foodPreferences(userProfile.getFoodPreferences())
				.userImageUrl(result.getImageUrlsAsList())
				.build();
			})
			.toList();
	}

	@Transactional(readOnly = true)
	public PageResponse<SendedAccompany> getAllSendedAccompanies(PageRequest request, Long applicantId){
		Slice<FindBoardThumbnailsResult> sliceResult = accompanyRequestRepository.findBoardThumbnails(request.cursor(), request.size(), applicantId);

		List<SendedAccompany> sendedAccompanies = getSendedAccompanies(sliceResult.getContent());

		return new PageResponse<>(sliceResult.hasNext(), sendedAccompanies, getLastCursor(sliceResult.getContent()));
	}

	/**
	 * imageUrls의 타입을 String -> List<String>로 변환합니다.
	 */
	private static List<SendedAccompany> getSendedAccompanies(List<FindBoardThumbnailsResult> results) {
		List<SendedAccompany> sendedAccompanies = results.stream()
			.map(result -> SendedAccompany.builder()
				.requestId(result.getRequestId())
				.title(result.getTitle())
				.region(result.getRegion())
				.startDate(result.getStartDate())
				.endDate(result.getEndDate())
				.nickname(result.getNickname())
				.imageUrls(result.getImageUrlsAsList())
				.build())
			.toList();
		return sendedAccompanies;
	}

	@Transactional
	public void deleteByBoardId(Long boardId) {
		accompanyRequestRepository.deleteByAccompanyBoardId(boardId);
	}

	@Transactional
	public Long getApplicantId(Long requestId) {
		return accompanyRequestRepository.findUserId(requestId)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
	}
	@Transactional(readOnly = true)
	public AccompanyRequestDetailInfo getRequestDetailInfo(Long boardId, Long userId, boolean isReceived) {
		AccompanyRequest accompanyRequest = accompanyRequestRepository.findRequestDetailInfo(boardId, userId)
            .orElseThrow(() -> new AccompanyRequestNotFoundException(ErrorCode.ACCOMPANY_REQUEST_NOT_FOUND));

        return AccompanyRequestDetailInfo.builder()
            .requestId(accompanyRequest.getId())
			.userId(accompanyRequest.getUser().getId())
			.introduce(accompanyRequest.getIntroduce())
			.chatLink(accompanyRequest.getChatLink())
			.isReceived(isReceived)
			.build();
	}

	public Long getBoardId(Long requestId){
		return accompanyRequestRepository.findBoardId(requestId)
			.orElseThrow(() -> new AccompanyBoardNotFoundException(ErrorCode.ACCOMPANY_BOARD_NOT_FOUND));
	}

	public AccompanyBoard getAccompanyBoard(Long boardId) {
		return AccompanyBoard.builder().id(boardId).build();
	}

	@Transactional
	public void deleteRequest(Long requestId, Long userId){
		AccompanyRequest accompanyRequest = accompanyRequestRepository.findById(requestId)
			.orElseThrow(() -> new AccompanyRequestNotFoundException(ErrorCode.ACCOMPANY_REQUEST_NOT_FOUND));

		Long requestUserId = accompanyRequest.getUser().getId();

		if(userId != requestUserId)
			throw new BadRequestException(ErrorCode.ACCESS_DENIED);

		accompanyRequestRepository.delete(accompanyRequest);
	}
}
