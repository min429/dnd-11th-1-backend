package com.dnd.accompany.domain.user.infrastructure;

import com.dnd.accompany.domain.user.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserId(Long userId);

    List<UserProfile> findByUserIdIn(Set<Long> userIds);
}
