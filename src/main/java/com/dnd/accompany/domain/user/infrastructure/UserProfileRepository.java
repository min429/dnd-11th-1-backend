package com.dnd.accompany.domain.user.infrastructure;

import com.dnd.accompany.domain.user.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
