package com.dnd.accompany.domain.review.infrastructure;

import com.dnd.accompany.domain.review.api.dto.SimpleEvaluationResult;
import com.dnd.accompany.domain.review.api.dto.SimpleReviewResult;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dnd.accompany.domain.accompany.entity.QAccompanyBoard.accompanyBoard;
import static com.dnd.accompany.domain.review.entity.QPersonality.personality;
import static com.dnd.accompany.domain.review.entity.QReview.review;
import static com.dnd.accompany.domain.review.entity.QTravelPreference.travelPreference;
import static com.dnd.accompany.domain.review.entity.QTravelStyle.travelStyle;
import static com.dnd.accompany.domain.user.entity.QUser.user;
import static com.querydsl.jpa.JPAExpressions.*;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SimpleReviewResult> findAllByReceiverId(Long receiverId) {
        return jpaQueryFactory
                .select(Projections.constructor(SimpleReviewResult.class,
                        user.nickname,
                        user.profileImageUrl,
                        accompanyBoard.region,
                        accompanyBoard.startDate,
                        accompanyBoard.endDate,
                        review.detailContent
                ))
                .from(review)
                .join(accompanyBoard)
                    .on(review.accompanyBoardId.eq(accompanyBoard.id))
                .join(user)
                    .on(review.writerId.eq(user.id))
                .where(eqReceiver(receiverId))
                .fetch();
    }

    @Override
    public SimpleEvaluationResult findEvaluationsByReceiverId(Long userId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(SimpleEvaluationResult.class,
                                select(travelStyle.type)
                                        .from(review)
                                        .join(review.travelStyle, travelStyle)
                                        .where(eqReceiver(userId))
                                        .groupBy(travelStyle.type)
                                        .orderBy(travelStyle.type.count().desc())
                                        .limit(1),

                                select(travelPreference.type)
                                        .from(review)
                                        .join(review.travelPreference, travelPreference)
                                        .where(eqReceiver(userId))
                                        .groupBy(travelPreference.type)
                                        .orderBy(travelPreference.type.count().desc())
                                        .limit(1),

                                select(personality.type)
                                        .from(review)
                                        .join(review.personalityType, personality)
                                        .where(eqReceiver(userId))
                                        .groupBy(personality.type)
                                        .orderBy(personality.type.count().desc())
                                        .limit(1)
                        )
                )
                .from(review)
                .fetchFirst();
    }

    private BooleanExpression eqReceiver(Long receiverId) {
        return receiverId != null ? review.receiver.id.eq(receiverId) : null;
    }
}
