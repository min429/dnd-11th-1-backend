package com.dnd.accompany.domain.review.entity;

import com.dnd.accompany.domain.common.entity.TimeBaseEntity;
import com.dnd.accompany.domain.review.entity.enums.PersonalityType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review_personality")
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE review_personality SET deleted = true WHERE id = ?")
public class Personality extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PersonalityType type;

    private boolean deleted = Boolean.FALSE;
}
