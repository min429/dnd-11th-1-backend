package com.dnd.accompany.domain.review.infrastructure;

import com.dnd.accompany.domain.review.api.dto.SimpleReviewResult;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dnd.accompany.domain.accompany.entity.QAccompanyBoard.accompanyBoard;
import static com.dnd.accompany.domain.review.entity.QReview.review;
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
                .where(review.receiverId.eq(receiverId))
                .fetch();
    }
}
