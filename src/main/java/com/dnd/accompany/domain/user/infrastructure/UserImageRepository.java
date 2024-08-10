package com.dnd.accompany.domain.user.infrastructure;

import com.dnd.accompany.domain.user.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
}
