package com.dnd.accompany.domain.accompany.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.dnd.accompany.domain.accompany.entity.enums.RequestState;
import com.dnd.accompany.domain.common.entity.TimeBaseEntity;
import com.dnd.accompany.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "accompany_requests", indexes = {
	@Index(name = "IX_user_id", columnList = "user_id"),
	@Index(name = "IX_accompany_boards_id", columnList = "accompany_boards_id")
})
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE accompany_requests SET deleted = true WHERE id = ?")
public class AccompanyRequest extends TimeBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "accompany_boards_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private AccompanyBoard accompanyBoard;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RequestState requestState;

	@Column(
		length = 1500,
		nullable = false
	)
	private String introduce;

	@Column(
		length = 2000,
		nullable = false)
	private String chatLink;

	private boolean deleted = Boolean.FALSE;

	@Builder
	public AccompanyRequest(Long id, User user, AccompanyBoard accompanyBoard, RequestState requestState,
		String introduce, String chatLink) {
		this.id = id;
		this.user = user;
		this.accompanyBoard = accompanyBoard;
		this.requestState = requestState;
		this.introduce = introduce;
		this.chatLink = chatLink;
	}
}
