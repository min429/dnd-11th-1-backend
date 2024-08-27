package com.dnd.accompany.domain.user.entity;

import io.jsonwebtoken.lang.Assert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.dnd.accompany.domain.common.entity.TimeBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.springframework.util.Assert.notNull;

@Table(name = "users")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")
public class User extends TimeBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String provider;

	@Column(
		name = "oauth_id",
		nullable = false
	)
	private String oauthId;

	@Column(length = 1000)
	private String profileImageUrl;

	private int evaluationCount;

	private boolean deleted = false;

	public static User of(String nickname, String provider, String oauthId, String profileImageUrl) {
		return User.builder()
				.nickname(nickname)
				.provider(provider)
				.oauthId(oauthId)
				.profileImageUrl(profileImageUrl)
				.build();
	}

	public void updateUser(String nickname, String profileImageUrl) {
		notNull(nickname, "닉네임은 필수 값입니다.");

		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
	}

	public void addEvaluationCount(int count) {
		this.evaluationCount += count;
	}
}
