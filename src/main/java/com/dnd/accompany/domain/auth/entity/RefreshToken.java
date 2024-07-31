package com.dnd.accompany.domain.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "refresh_token")
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
	@Id
	private Long userId;

	private String token;

	private long expiredAt;

	@Builder
	private RefreshToken(String token, Long userId, long expiredAt) {
		this.token = token;
		this.userId = userId;
		this.expiredAt = expiredAt;
	}

	public void renew(String token, long expiredAt) {
		this.token = token;
		this.expiredAt = expiredAt;
	}
}
