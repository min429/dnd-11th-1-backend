package com.dnd.accompany.domain.review.infrastructure;

import com.dnd.accompany.domain.review.api.dto.SimpleReviewResult;
import java.util.List;

public interface ReviewRepositoryCustom {
    List<SimpleReviewResult> findAllByReceiverId(Long receiverId);
}
