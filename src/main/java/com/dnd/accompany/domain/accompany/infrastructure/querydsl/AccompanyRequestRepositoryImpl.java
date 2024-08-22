package com.dnd.accompany.domain.accompany.infrastructure.querydsl;

import static com.dnd.accompany.domain.accompany.entity.QAccompanyBoard.*;
import static com.dnd.accompany.domain.accompany.entity.QAccompanyImage.*;
import static com.dnd.accompany.domain.accompany.entity.QAccompanyRequest.*;
import static com.dnd.accompany.domain.accompany.entity.QAccompanyUser.*;
import static com.dnd.accompany.domain.accompany.entity.enums.RequestState.*;
import static com.dnd.accompany.domain.accompany.entity.enums.Role.*;
import static com.dnd.accompany.domain.user.entity.QUser.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.dnd.accompany.domain.accompany.api.dto.FindBoardThumbnailsResult;
import com.dnd.accompany.domain.accompany.infrastructure.querydsl.interfaces.AccompanyRequestRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccompanyRequestRepositoryImpl implements AccompanyRequestRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	private BooleanExpression isHost() {
		return accompanyUser.role.eq(HOST);
	}

	@Override
	public Slice<FindBoardThumbnailsResult> findBoardThumbnails(Pageable pageable, Long applicantId) {
		List<FindBoardThumbnailsResult> content = queryFactory
			.select(Projections.constructor(FindBoardThumbnailsResult.class,
				accompanyRequest.id,
				accompanyBoard.title,
				accompanyBoard.region,
				accompanyBoard.startDate,
				accompanyBoard.endDate,
				user.nickname,
				Expressions.stringTemplate("GROUP_CONCAT(DISTINCT {0})", accompanyImage.imageUrl)))
			.from(accompanyUser)
			.join(accompanyUser.accompanyBoard, accompanyBoard)
			.join(accompanyUser.user, user)
			.join(accompanyRequest).on(accompanyRequest.accompanyBoard.id.eq(accompanyBoard.id))
			.leftJoin(accompanyImage).on(accompanyImage.accompanyBoard.id.eq(accompanyBoard.id))
			.where(isHost())
			.where(accompanyRequest.user.id.eq(applicantId))
			.where(accompanyRequest.requestState.eq(HOLDING))
			.groupBy(accompanyRequest.id, accompanyBoard.title, accompanyBoard.region,
				accompanyBoard.startDate, accompanyBoard.endDate, user.nickname)
			.orderBy(accompanyBoard.updatedAt.desc(), accompanyBoard.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		boolean hasNext = content.size() > pageable.getPageSize();

		if (hasNext) {
			content.remove(content.size() - 1);
		}

		return new SliceImpl<>(content, pageable, hasNext);
	}
}
