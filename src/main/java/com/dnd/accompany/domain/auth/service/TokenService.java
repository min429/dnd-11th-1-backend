package com.dnd.accompany.domain.auth.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dnd.accompany.domain.auth.dto.AuthUserInfo;
import com.dnd.accompany.domain.auth.dto.Tokens;
import com.dnd.accompany.domain.auth.entity.RefreshToken;
import com.dnd.accompany.domain.auth.exception.ExpiredTokenException;
import com.dnd.accompany.domain.auth.exception.RefreshTokenNotFoundException;
import com.dnd.accompany.domain.auth.infrastructure.RefreshTokenRepository;
import com.dnd.accompany.global.common.response.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
	private static final long ONE_WEEK_IN_MILLI_SECONDS = 604800000L;

	private final JwtTokenService jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	@Value("${jwt.expiry-seconds.refresh-token}")
	private long refreshTokenExpireSeconds;

	public Tokens createTokens(AuthUserInfo authUserInfo) {
		Long userId = authUserInfo.getUserId();

		String accessToken = createAccessToken(userId);
		String refreshToken = createRefreshToken(userId);

		return new Tokens(accessToken, refreshToken);
	}

	private String createAccessToken(Long userId) {
		return jwtTokenProvider.getAccessToken(userId);
	}

	private String createRefreshToken(Long userId) {
		long expiredAtInMillis = System.currentTimeMillis() + refreshTokenExpireSeconds * 1000;

		RefreshToken refreshToken = RefreshToken.builder()
			.token(UUID.randomUUID().toString())
			.userId(userId)
			.expiredAt(expiredAtInMillis)
			.build();

		return refreshTokenRepository.save(refreshToken).getToken();
	}

	public Tokens getAccessTokensByRefreshToken(String refreshToken) {
		RefreshToken token = refreshTokenRepository.findRefreshTokenByToken(refreshToken)
			.orElseThrow(() -> new RefreshTokenNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

		validateExpiration(token);

		long oneWeekFromNow = System.currentTimeMillis() + ONE_WEEK_IN_MILLI_SECONDS;
		boolean isExpiringSoon = token.getExpiredAt() < oneWeekFromNow;

		if (isExpiringSoon) {
			String renewalRefreshToken = UUID.randomUUID().toString();
			token.renew(renewalRefreshToken, refreshTokenExpireSeconds);

			refreshTokenRepository.save(token);
		}

		String accessToken = createAccessToken(token.getUserId());
		return new Tokens(accessToken, token.getToken());
	}

	private void validateExpiration(RefreshToken token) {
		long expiredAt = token.getExpiredAt();
		long currentTime = System.currentTimeMillis();

		if (expiredAt < currentTime) {
			throw new ExpiredTokenException(ErrorCode.REFRESH_TOKEN_EXPIRED);
		}
	}

	public void deleteRefreshToken(String refreshToken) {
		refreshTokenRepository.findRefreshTokenByToken(refreshToken)
			.ifPresent(refreshTokenRepository::delete);
	}

}
