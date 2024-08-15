package com.dnd.accompany.domain.accompany.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dnd.accompany.domain.accompany.entity.AccompanyBoard;
import com.dnd.accompany.domain.accompany.infrastructure.querydsl.interfaces.AccompanyBoardRepositoryCustom;

public interface AccompanyBoardRepository extends JpaRepository<AccompanyBoard, Long>, AccompanyBoardRepositoryCustom {
	@Query("SELECT b FROM AccompanyBoard b JOIN FETCH b.categories WHERE b.id = :boardId")
	Optional<AccompanyBoard> findByIdWithCategories(Long boardId);
}
