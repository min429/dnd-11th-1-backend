package com.dnd.accompany.domain.review.infrastructure;

import com.dnd.accompany.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
