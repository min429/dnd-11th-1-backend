package com.dnd.accompany.domain.accompany.service;

import static com.dnd.accompany.domain.accompany.entity.enums.RequestState.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.accompany.domain.accompany.api.dto.AccompanyRequestDetailInfo;
import com.dnd.accompany.domain.accompany.api.dto.CreateAccompanyRequest;
import com.dnd.accompany.domain.accompany.entity.AccompanyBoard;
import com.dnd.accompany.domain.accompany.entity.AccompanyRequest;
import com.dnd.accompany.domain.accompany.entity.enums.Role;
import com.dnd.accompany.domain.accompany.exception.accompanyrequest.AccompanyRequestNotFoundException;
import com.dnd.accompany.domain.accompany.infrastructure.AccompanyRequestRepository;
import com.dnd.accompany.domain.user.entity.User;
import com.dnd.accompany.global.common.response.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccompanyRequestService {
	private final AccompanyRequestRepository accompanyRequestRepository;

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

	@Transactional
	public void deleteByBoardId(Long boardId) {
		accompanyRequestRepository.deleteByAccompanyBoardId(boardId);
	}

	@Transactional(readOnly = true)
	public AccompanyRequestDetailInfo getRequestDetailInfo(Long boardId, Long userId) {
		AccompanyRequest accompanyRequest = accompanyRequestRepository.findRequestDetailInfo(boardId, userId)
            .orElseThrow(() -> new AccompanyRequestNotFoundException(ErrorCode.ACCOMPANY_REQUEST_NOT_FOUND));

        return AccompanyRequestDetailInfo.builder()
            .boardId(accompanyRequest.getAccompanyBoard().getId())
			.userId(accompanyRequest.getUser().getId())
			.introduce(accompanyRequest.getIntroduce())
			.chatLink(accompanyRequest.getChatLink())
			.build();
	}

	public AccompanyBoard getAccompanyBoard(Long boardId) {
		return AccompanyBoard.builder().id(boardId).build();
	}
}
