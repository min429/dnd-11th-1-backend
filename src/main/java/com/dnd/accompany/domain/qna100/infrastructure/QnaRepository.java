package com.dnd.accompany.domain.qna100.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd.accompany.domain.qna100.entity.Qna;
import com.dnd.accompany.domain.qna100.infrastructure.querydsl.interfaces.QnaRepositoryCustom;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long>, QnaRepositoryCustom {
	void deleteAllByUserId(Long userId);
}
