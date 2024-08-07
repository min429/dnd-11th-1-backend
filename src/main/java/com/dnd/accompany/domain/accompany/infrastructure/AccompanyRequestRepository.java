package com.dnd.accompany.domain.accompany.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd.accompany.domain.accompany.entity.AccompanyRequest;

@Repository
public interface AccompanyRequestRepository extends JpaRepository<AccompanyRequest, Long> {
	void deleteByAccompanyBoardId(Long boardId);
}
