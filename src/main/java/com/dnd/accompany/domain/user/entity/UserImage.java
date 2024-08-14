package com.dnd.accompany.domain.user.entity;

import com.dnd.accompany.domain.common.entity.TimeBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "user_images")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE user_images SET deleted = true WHERE id = ?")
public class UserImage extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 1000)
    private String imageUrl;

    private boolean deleted = Boolean.FALSE;

    public static UserImage of(Long userId, String imageUrl) {
        return UserImage.builder()
                .userId(userId)
                .imageUrl(imageUrl)
                .build();
    }
}
