package com.dnd.accompany.domain.accompany.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.accompany.domain.accompany.api.dto.UserProfileThumbnail;
import com.dnd.accompany.domain.accompany.entity.AccompanyBoard;
import com.dnd.accompany.domain.accompany.entity.AccompanyUser;
import com.dnd.accompany.domain.accompany.entity.enums.Role;
import com.dnd.accompany.domain.accompany.infrastructure.AccompanyUserRepository;
import com.dnd.accompany.domain.user.entity.User;
import com.dnd.accompany.domain.user.exception.UserNotFoundException;
import com.dnd.accompany.domain.user.exception.UserProfileNotFoundException;
import com.dnd.accompany.global.common.response.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccompanyUserService {
	private final AccompanyUserRepository accompanyUserRepository;

	@Transactional
	public void save(Long userId, AccompanyBoard accompanyBoard, Role role) {
		accompanyUserRepository.save(AccompanyUser.builder()
			.accompanyBoard(accompanyBoard)
			.user(User.builder().id(userId).build())
			.role(role)
			.build());
	}

	@Transactional(readOnly = true)
	public UserProfileThumbnail getUserProfileThumbnail(Long boardId) {
		Long userId = accompanyUserRepository.findUserIdByAccompanyBoardId(boardId)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

		UserProfileThumbnail profileThumbnail = accompanyUserRepository.findUserProfileThumbnail(userId)
			.orElseThrow(() -> new UserProfileNotFoundException(ErrorCode.PROFILE_NOT_FOUND));
		return profileThumbnail;
	}

	@Transactional(readOnly = true)
	public Long getHostIdByAccompanyBoardId(Long boardId){
		return accompanyUserRepository.findHostIdByAccompanyBoardId(boardId)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
	}

	@Transactional
	public void deleteByBoardId(Long boardId) {
		accompanyUserRepository.deleteByAccompanyBoardId(boardId);
	}

	@Transactional(readOnly = true)
	public String getNickname(Long userId){
		return accompanyUserRepository.findNickname(userId)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
	}
}
