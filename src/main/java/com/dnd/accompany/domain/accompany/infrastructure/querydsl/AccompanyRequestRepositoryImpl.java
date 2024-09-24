package com.dnd.accompany.domain.accompany.infrastructure.querydsl;

import static com.dnd.accompany.domain.accompany.api.dto.FindSlicesResult.*;
import static com.dnd.accompany.domain.accompany.api.dto.PageRequest.*;
import static com.dnd.accompany.domain.accompany.entity.QAccompanyBoard.*;
import static com.dnd.accompany.domain.accompany.entity.QAccompanyImage.*;
import static com.dnd.accompany.domain.accompany.entity.QAccompanyRequest.*;
import static com.dnd.accompany.domain.accompany.entity.QAccompanyUser.*;
import static com.dnd.accompany.domain.accompany.entity.enums.RequestState.*;
import static com.dnd.accompany.domain.accompany.entity.enums.Role.*;
import static com.dnd.accompany.domain.user.entity.QUser.*;
import static com.dnd.accompany.domain.user.entity.QUserImage.*;

import java.util.List;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import com.dnd.accompany.domain.accompany.api.dto.FindApplicantDetailsResult;
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
	public Slice<FindBoardThumbnailsResult> findBoardThumbnails(String cursor, int size, Long applicantId) {
		List<FindBoardThumbnailsResult> content = queryFactory
			.select(Projections.constructor(FindBoardThumbnailsResult.class,
				accompanyRequest.id,
				accompanyBoard.title,
				accompanyBoard.region,
				accompanyBoard.startDate,
				accompanyBoard.endDate,
				user.nickname,
				Expressions.stringTemplate("GROUP_CONCAT(DISTINCT {0})", accompanyImage.imageUrl),
				Expressions.stringTemplate(
					"CONCAT(DATE_FORMAT({0}, '%Y%m%d%H%i%S'), LPAD(CAST({1} AS STRING), 6, '0'))",
					accompanyRequest.updatedAt,
					accompanyRequest.id
				))
			)
			.from(accompanyUser)
			.join(accompanyUser.accompanyBoard, accompanyBoard)
			.join(accompanyUser.user, user)
			.join(accompanyRequest).on(accompanyRequest.accompanyBoard.id.eq(accompanyBoard.id))
			.leftJoin(accompanyImage).on(accompanyImage.accompanyBoard.id.eq(accompanyBoard.id))
			.where(isHost())
			.where(accompanyRequest.user.id.eq(applicantId))
			.where(accompanyRequest.requestState.eq(HOLDING))
			.where(cursorCondition(cursor, accompanyRequest.updatedAt, accompanyRequest.id))
			.groupBy(accompanyRequest.id, accompanyBoard.title, accompanyBoard.region, accompanyBoard.startDate,
				accompanyBoard.endDate, user.nickname)
			.orderBy(accompanyRequest.updatedAt.desc(), accompanyRequest.id.desc())
			.limit(size + 1)
			.fetch();

		return createSlice(size, content);
	}

	@Override
	public Slice<FindApplicantDetailsResult> findApplicantDetails(String cursor, int size, Long hostId) {
		List<FindApplicantDetailsResult> content = queryFactory
			.select(Projections.constructor(FindApplicantDetailsResult.class,
				accompanyRequest.id,
				user.id,
				user.nickname,
				user.provider,
				user.profileImageUrl,
				Expressions.stringTemplate("GROUP_CONCAT(DISTINCT {0})", userImage.imageUrl),
				Expressions.stringTemplate(
					"CONCAT(DATE_FORMAT({0}, '%Y%m%d%H%i%S'), LPAD(CAST({1} AS STRING), 6, '0'))",
					accompanyRequest.updatedAt,
					accompanyRequest.id
				))
			)
			.from(accompanyUser)
			.join(accompanyUser.accompanyBoard, accompanyBoard)
			.join(accompanyRequest).on(accompanyRequest.accompanyBoard.id.eq(accompanyBoard.id))
			.join(user).on(user.id.eq(accompanyRequest.user.id))
			.leftJoin(userImage).on(userImage.userId.eq(user.id))
			.where(isHost())
			.where(accompanyUser.user.id.eq(hostId))
			.where(accompanyRequest.requestState.eq(HOLDING))
			.where(cursorCondition(cursor, accompanyRequest.updatedAt, accompanyRequest.id))
			.groupBy(accompanyRequest.id, user.id, user.nickname, user.provider,
				user.profileImageUrl)
			.orderBy(accompanyRequest.updatedAt.desc(), accompanyRequest.id.desc())
			.limit(size + 1)
			.fetch();

		return createSlice(size, content);
	}

	@Override
	public boolean existsBy(Long userId, Long boardId) {
		return queryFactory
			.selectOne()
			.from(accompanyRequest)
			.where(accompanyRequest.user.id.eq(userId))
			.where(accompanyRequest.accompanyBoard.id.eq(boardId))
			.fetchFirst() != null;
	}
}
