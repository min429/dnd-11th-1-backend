package com.dnd.accompany.domain.review.service;

import com.dnd.accompany.domain.accompany.infrastructure.AccompanyBoardRepository;
import com.dnd.accompany.domain.review.api.dto.CreateReviewRequest;
import com.dnd.accompany.domain.review.entity.Review;
import com.dnd.accompany.domain.review.infrastructure.ReviewRepository;
import com.dnd.accompany.domain.user.infrastructure.UserRepository;
import com.dnd.accompany.global.common.exception.NotFoundException;
import com.dnd.accompany.global.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final AccompanyBoardRepository accompanyBoardRepository;

    public Long create(Long userId, CreateReviewRequest request) {
        validateWriter(userId);
        validateAccompanyBoard(request.accompanyBoardId());

        Review review = Review.of(
                userId,
                request.accompanyBoardId(),
                request.satisfactionLevel(),
                request.recommendationStatus(),
                request.companionType(),
                request.personalityType(),
                request.travelPreference(),
                request.travelStyle(),
                request.detailContent(),
                request.reviewImageUrls()
        );

        reviewRepository.save(review);

        return review.getId();
    }

    private void validateWriter(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private void validateAccompanyBoard(Long accompanyBoardId) {
        accompanyBoardRepository.findById(accompanyBoardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ACCOMPANY_BOARD_NOT_FOUND));
    }
}
