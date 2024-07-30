package com.dnd.accompany.domain.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dnd.accompany.domain.auth.exception.ExpiredTokenException;
import com.dnd.accompany.domain.auth.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;

class JwtTokenServiceTest {

	private static final String ISSUER = "issuer";
	private static final String SECRET_KEY = "matetripSecretKeyForTestttttttqqwerqwerqwerqwerqwerqwer";
	private static final int ACCESS_TOKEN_EXPIRY_SECONDS = 3;

	private static final long USER_ID = 100L;
	private final JwtTokenService jwtTokenProvider
		= new JwtTokenService(ISSUER, SECRET_KEY, ACCESS_TOKEN_EXPIRY_SECONDS);

	private String accessToken;

	@Test
	@DisplayName("페이로드(userId)를 담은 JWT를 생성 및 추출할 수 있다.")
	void success1() {
		// when
		String accessToken = jwtTokenProvider.getAccessToken(USER_ID);
		Claims claims = jwtTokenProvider.getClaims(accessToken);

		//then
		assertDoesNotThrow(() -> jwtTokenProvider.getAccessToken(USER_ID));

		assertThat(claims)
			.containsEntry("userId", Long.valueOf(USER_ID).intValue());
	}

	@Test
	@DisplayName("유효한 토큰의 경우 검증 시 예외가 발생하지 않는다.")
	void success2() {
		// given
		accessToken = jwtTokenProvider.getAccessToken(USER_ID);

		// when & then
		assertDoesNotThrow(() -> jwtTokenProvider.validateToken(accessToken));
	}

	@Test
	@DisplayName("토큰의 만료 시간이 지나면 ExpiredTokenProblem이 발생한다.")
	void fail1() throws Exception {
		// given
		accessToken = jwtTokenProvider.getAccessToken(USER_ID);

		Thread.sleep(ACCESS_TOKEN_EXPIRY_SECONDS * 1000L);

		// when & then
		assertThatThrownBy(() -> jwtTokenProvider.validateToken(accessToken))
			.isInstanceOf(ExpiredTokenException.class);
	}

	@Test
	@DisplayName("유효하지 않은 토큰일 경우 InvalidTokenProblem이 발생한다.")
	void fail2() {
		// given
		accessToken = "InvalidToken";

		// when & then
		assertThatThrownBy(() -> jwtTokenProvider.validateToken(accessToken))
			.isInstanceOf(InvalidTokenException.class);
	}

	@Test
	@DisplayName("토큰 값이 null인 경우 InvalidTokenProblem 발생한다.")
	void fail3() {
		// given
		accessToken = null;

		// when & then
		assertThatThrownBy(() -> jwtTokenProvider.validateToken(accessToken))
			.isInstanceOf(InvalidTokenException.class);
	}

	@Test
	@DisplayName("올바르지 않은 키로 검증 시 예외가 발생한다.")
	void fail4() {
		// given
		String invalidSecretKey = "matetripInvalidSecretKey12341234123412341234.";

		JwtTokenService wrongTokenProvider
			= new JwtTokenService(ISSUER, invalidSecretKey, ACCESS_TOKEN_EXPIRY_SECONDS);

		//when
		accessToken = wrongTokenProvider.getAccessToken(USER_ID);

		//then
		assertThatThrownBy(() -> jwtTokenProvider.validateToken(accessToken))
			.isInstanceOf(InvalidTokenException.class);
	}

}
