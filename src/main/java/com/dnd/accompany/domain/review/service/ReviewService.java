package com.dnd.accompany.domain.review.service;

import com.dnd.accompany.domain.accompany.entity.AccompanyBoard;
import com.dnd.accompany.domain.accompany.infrastructure.AccompanyBoardRepository;
import com.dnd.accompany.domain.review.api.dto.CreateReviewRequest;
import com.dnd.accompany.domain.review.api.dto.ReviewDetailsResult;
import com.dnd.accompany.domain.review.api.dto.SimpleEvaluationResponse;
import com.dnd.accompany.domain.review.api.dto.SimpleEvaluationResult;
import com.dnd.accompany.domain.review.api.dto.SimpleReviewResponses;
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
        User receiver = getUser(request.receiverId());

        getAccompanyBoard(request.accompanyBoardId());

        Review review = Review.createReview(
                userId,
                receiver,
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

        User writer = getUser(review.getWriterId());
        AccompanyBoard accompanyBoard = getAccompanyBoard(review.getAccompanyBoardId());

        return ReviewDetailsResult.of(writer, review, accompanyBoard);
    }

    @Transactional(readOnly = true)
    public SimpleReviewResponses getReviewList(Long userId) {
        List<SimpleReviewResult> results = reviewRepository.findAllByReceiverId(userId);
        int totalCount = results.size();

        return new SimpleReviewResponses(results, totalCount);
    }

    private Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.REVIEW_NOT_FOUND));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private AccompanyBoard getAccompanyBoard(Long accompanyBoardId) {
        return accompanyBoardRepository.findById(accompanyBoardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ACCOMPANY_BOARD_NOT_FOUND));
    }

    private void validateReceiver(Long userId, Review review) {
        if (!review.isReceiver(userId)) {
            throw new BadRequestException(ErrorCode.ACCESS_DENIED);
        }
    }

    public SimpleEvaluationResponse getEvaluation(Long userId) {
        User receiver = getUser(userId);
        SimpleEvaluationResult result = reviewRepository.findEvaluationsByReceiverId(userId);

        return SimpleEvaluationResponse.builder()
                .personalityType(result.getPersonalityType())
                .travelPreference(result.getTravelPreference())
                .travelStyle(result.getTravelStyle())
                .evaluationCount(receiver.getEvaluationCount())
                .build();
    }
}
