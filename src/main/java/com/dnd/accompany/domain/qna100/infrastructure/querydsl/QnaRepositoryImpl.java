package com.dnd.accompany.domain.qna100.infrastructure.querydsl;

import org.springframework.stereotype.Repository;

import com.dnd.accompany.domain.qna100.infrastructure.querydsl.interfaces.QnaRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QnaRepositoryImpl implements QnaRepositoryCustom {

	private final JPAQueryFactory queryFactory;



}
