package com.dnd.accompany.domain.review.service;

import com.dnd.accompany.domain.accompany.entity.AccompanyBoard;
import com.dnd.accompany.domain.accompany.infrastructure.AccompanyBoardRepository;
import com.dnd.accompany.domain.review.api.dto.CreateReviewRequest;
import com.dnd.accompany.domain.review.api.dto.ReviewDetailsResult;
import com.dnd.accompany.domain.review.api.dto.SimpleReviewResponse;
import com.dnd.accompany.domain.review.api.dto.SimpleReviewResult;
import com.dnd.accompany.domain.review.entity.Review;
import com.dnd.accompany.domain.review.infrastructure.ReviewRepository;
import com.dnd.accompany.domain.user.entity.User;
import com.dnd.accompany.domain.user.infrastructure.UserRepository;
import com.dnd.accompany.global.common.exception.BadRequestException;
import com.dnd.accompany.global.common.exception.NotFoundException;
import com.dnd.accompany.global.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final AccompanyBoardRepository accompanyBoardRepository;

    @Transactional
    public Long create(Long userId, CreateReviewRequest request) {
        getWriter(userId);

        getAccompanyBoard(request.accompanyBoardId());

        Review review = Review.createReview(
                userId,
                request.receiverId(),
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

    @Transactional(readOnly = true)
    public ReviewDetailsResult getReviewDetails(Long userId, Long reviewId) {
        Review review = getReview(reviewId);

        validateReceiver(userId, review);

        User writer = getWriter(review.getWriterId());
        AccompanyBoard accompanyBoard = getAccompanyBoard(review.getAccompanyBoardId());

        return ReviewDetailsResult.of(writer, review, accompanyBoard);
    }

    @Transactional(readOnly = true)
    public List<SimpleReviewResponse> getReviewList(Long userId) {
        List<SimpleReviewResult> results = reviewRepository.findAllByReceiverId(userId);

        return results.stream()
                .map(result -> SimpleReviewResponse.builder()
                        .nickname(result.getNickname())
                        .profileImageUrl(result.getProfileImageUrl())
                        .detailContent(result.getDetailContent())
                        .region(result.getRegion())
                        .startDate(result.getStartDate())
                        .endDate(result.getEndDate())
                        .build()
                )
                .toList();
    }

    private Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.REVIEW_NOT_FOUND));
    }

    private User getWriter(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private AccompanyBoard getAccompanyBoard(Long accompanyBoardId) {
        return accompanyBoardRepository.findById(accompanyBoardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ACCOMPANY_BOARD_NOT_FOUND));
    }

    private void validateReceiver(Long userId, Review review) {
        if (!userId.equals(review.getReceiverId())) {
            throw new BadRequestException(ErrorCode.ACCESS_DENIED);
        }
    }
}
