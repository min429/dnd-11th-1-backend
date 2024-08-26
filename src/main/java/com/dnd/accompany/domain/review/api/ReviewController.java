package com.dnd.accompany.domain.review.api;

import com.dnd.accompany.domain.auth.dto.jwt.JwtAuthentication;
import com.dnd.accompany.domain.review.api.dto.CreateReviewRequest;
import com.dnd.accompany.domain.review.api.dto.ReviewDetailsResponse;
import com.dnd.accompany.domain.review.api.dto.ReviewDetailsResult;
import com.dnd.accompany.domain.review.api.dto.SimpleReviewResponse;
import com.dnd.accompany.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Review")
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 생성")
    @PostMapping
    public ResponseEntity<Long> create(
            @AuthenticationPrincipal JwtAuthentication user,
            @RequestBody @Valid CreateReviewRequest request
    ) {
        Long id = reviewService.create(user.getId(), request);

        return ResponseEntity.ok(id);
    }

    @Operation(summary = "리뷰 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDetailsResponse> getDetails(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("id") Long reviewId
    ) {
        ReviewDetailsResult result = reviewService.getReviewDetails(user.getId(), reviewId);
        ReviewDetailsResponse response = ReviewDetailsResponse.of(result);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "받은 리뷰 목록 조회")
    @GetMapping
    public ResponseEntity<List<SimpleReviewResponse>> getReviewList(
            @AuthenticationPrincipal JwtAuthentication user
    ) {
        List<SimpleReviewResponse> responses = reviewService.getReviewList(user.getId());
        return ResponseEntity.ok(responses);
    }
}
