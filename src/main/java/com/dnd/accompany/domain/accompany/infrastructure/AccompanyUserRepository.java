package com.dnd.accompany.domain.accompany.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd.accompany.domain.accompany.entity.AccompanyUser;

@Repository
public interface AccompanyUserRepository extends JpaRepository<AccompanyUser, Long> {
	void deleteByAccompanyBoardId(Long boardId);
}
