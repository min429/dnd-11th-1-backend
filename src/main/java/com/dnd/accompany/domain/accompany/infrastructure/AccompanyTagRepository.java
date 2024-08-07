package com.dnd.accompany.domain.accompany.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd.accompany.domain.accompany.entity.AccompanyTag;

@Repository
public interface AccompanyTagRepository extends JpaRepository<AccompanyTag, Long> {
	void deleteByAccompanyBoardId(Long boardId);
}
