package com.dnd.accompany.domain.user.infrastructure;

import com.dnd.accompany.domain.user.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
    List<UserImage> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
