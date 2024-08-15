package com.dnd.accompany.domain.accompany.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dnd.accompany.domain.accompany.entity.AccompanyImage;

@Repository
public interface AccompanyImageRepository extends JpaRepository<AccompanyImage, Long> {
	void deleteByAccompanyBoardId(Long boardId);

	@Query("SELECT i.imageUrl FROM AccompanyImage i WHERE i.accompanyBoard.id = :boardId")
	List<String> findImageUrlsByAccompanyBoardId(Long boardId);
}
