package com.dnd.accompany.domain.auth.dto.jwt;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.dnd.accompany.domain.auth.exception.InvalidTokenException;
import com.dnd.accompany.global.common.response.ErrorCode;

import lombok.Getter;

@Getter
public class JwtAuthentication {

	private Long id;
	private String accessToken;

	public JwtAuthentication(Long id, String accessToken) {
		this.id = validateId(id);
		this.accessToken = validateAccessToken(accessToken);
	}

	private Long validateId(Long id) {
		if (Objects.isNull(id) || id <= 0L) {
			throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
		}

		return id;
	}

	private String validateAccessToken(String accessToken) {
		if (StringUtils.isEmpty(accessToken)) {
			throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
		}

		return accessToken;
	}
}
