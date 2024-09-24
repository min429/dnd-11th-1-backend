package com.dnd.accompany.domain.accompany.infrastructure.querydsl.interfaces;

import java.util.Optional;

import com.dnd.accompany.domain.accompany.api.dto.UserProfileThumbnail;
import com.dnd.accompany.domain.accompany.entity.enums.Role;

public interface AccompanyUserRepositoryCustom {
	Optional<UserProfileThumbnail> findUserProfileThumbnail(Long userId);

	Optional<String> findNickname(Long userId);

	boolean existsBy(Long userId, Long boardId, Role role);
}
