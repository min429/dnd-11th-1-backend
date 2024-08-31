package com.dnd.accompany.domain.review.infrastructure;

import com.dnd.accompany.domain.review.api.dto.SimpleEvaluationResult;
import com.dnd.accompany.domain.review.api.dto.SimpleReviewResult;
import com.dnd.accompany.domain.review.api.dto.TypeCountResult;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
        List<TypeCountResult> travelStyleCounts = jpaQueryFactory
                .select(Projections.constructor(TypeCountResult.class,
                        travelStyle.type.stringValue(),
                        travelStyle.type.count()
                ))
                .from(travelStyle)
                .join(travelStyle.review, review)
                .where(eqReceiver(userId))
                .groupBy(travelStyle.type)
                .fetch();

        List<TypeCountResult> travelPreferenceCounts = jpaQueryFactory
                .select(Projections.constructor(TypeCountResult.class,
                        travelPreference.type.stringValue(),
                        travelPreference.type.count()
                ))
                .from(travelPreference)
                .join(travelPreference.review, review)
                .where(eqReceiver(userId))
                .groupBy(travelPreference.type)
                .fetch();

        List<TypeCountResult> personalityCounts = jpaQueryFactory
                .select(Projections.constructor(TypeCountResult.class,
                        personality.type.stringValue(),
                        personality.type.count()
                ))
                .from(personality)
                .join(personality.review, review)
                .where(eqReceiver(userId))
                .groupBy(personality.type)
                .fetch();

        return new SimpleEvaluationResult(travelStyleCounts, travelPreferenceCounts, personalityCounts);
    }

    private BooleanExpression eqReceiver(Long receiverId) {
        return receiverId != null ? review.receiver.id.eq(receiverId) : null;
    }
}
