package com.dnd.accompany.domain.accompany.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dnd.accompany.domain.accompany.entity.AccompanyRequest;
import com.dnd.accompany.domain.accompany.infrastructure.querydsl.interfaces.AccompanyRequestRepositoryCustom;

public interface AccompanyRequestRepository extends JpaRepository<AccompanyRequest, Long>, AccompanyRequestRepositoryCustom {
	void deleteByAccompanyBoardId(Long boardId);

	@Query("SELECT r FROM AccompanyRequest r WHERE r.accompanyBoard.id = :boardId AND r.user.id = :userId")
	Optional<AccompanyRequest> findRequestDetailInfo(Long boardId, Long userId);
}
