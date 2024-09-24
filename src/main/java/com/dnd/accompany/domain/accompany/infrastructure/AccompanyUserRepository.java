package com.dnd.accompany.domain.accompany.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dnd.accompany.domain.accompany.entity.AccompanyUser;
import com.dnd.accompany.domain.accompany.entity.enums.Role;
import com.dnd.accompany.domain.accompany.infrastructure.querydsl.interfaces.AccompanyUserRepositoryCustom;

public interface AccompanyUserRepository extends JpaRepository<AccompanyUser, Long>, AccompanyUserRepositoryCustom {
	void deleteByAccompanyBoardId(Long boardId);

	@Query("SELECT u.user.id FROM AccompanyUser u WHERE u.accompanyBoard.id = :boardId AND u.role = 'HOST'")
	Optional<Long> findUserIdByAccompanyBoardId(Long boardId);

	@Query("SELECT u.user.id FROM AccompanyUser u WHERE u.accompanyBoard.id = :boardId AND u.role = 'HOST'")
	Optional<Long> findHostIdByAccompanyBoardId(Long boardId);
}
