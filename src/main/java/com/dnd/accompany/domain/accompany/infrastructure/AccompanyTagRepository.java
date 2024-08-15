package com.dnd.accompany.domain.accompany.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dnd.accompany.domain.accompany.entity.AccompanyTag;

@Repository
public interface AccompanyTagRepository extends JpaRepository<AccompanyTag, Long> {
	void deleteByAccompanyBoardId(Long boardId);

	@Query("SELECT t.name FROM AccompanyTag t WHERE t.accompanyBoard.id = :boardId")
	List<String> findNamesByAccompanyBoardId(Long boardId);
}
