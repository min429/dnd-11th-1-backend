package com.dnd.accompany.domain.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dnd.accompany.domain.auth.dto.AuthUserInfo;
import com.dnd.accompany.domain.auth.dto.Tokens;
import com.dnd.accompany.domain.auth.entity.RefreshToken;
import com.dnd.accompany.domain.auth.exception.ExpiredTokenException;
import com.dnd.accompany.domain.auth.exception.RefreshTokenNotFoundException;
import com.dnd.accompany.domain.auth.infrastructure.RefreshTokenRepository;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

	@Mock
	private RefreshTokenRepository refreshTokenRepository;

	@Mock
	private JwtTokenService jwtTokenProvider;

	@InjectMocks
	private TokenService tokenService;

	private static final long ONE_WEEK_IN_MILLI_SECONDS = 7 * 24 * 60 * 60 * 1000L;

	@Nested
	@DisplayName("UserID로 토큰 생성 요청 시")
	class createTokens {

		@Test
		@DisplayName("UserID가 유효한 값이면 정상 생성된다.")
		void success() {
			//given
			Long userId = 1L;
			AuthUserInfo authUserInfo = new AuthUserInfo(userId);

			String accessToken = "testAccessToken";
			String refreshTokenString = UUID.randomUUID().toString();

			RefreshToken refreshToken = RefreshToken.builder()
				.token(refreshTokenString)
				.userId(userId)
				.expiredAt(System.currentTimeMillis() + 1000)
				.build();

			//when
			when(jwtTokenProvider.getAccessToken(anyLong())).thenReturn(accessToken);
			when(refreshTokenRepository.save(any(RefreshToken.class))).thenReturn(refreshToken);

			Tokens tokens = tokenService.createTokens(authUserInfo);

			//then
			assertNotNull(tokens.accessToken());
			assertNotNull(tokens.refreshToken());
			assertEquals(accessToken, tokens.accessToken());
			assertEquals(refreshTokenString, tokens.refreshToken());

			verify(jwtTokenProvider, times(1)).getAccessToken(userId);
			verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
		}
	}

	@Nested
	@DisplayName("RT로 AccessToken 갱신 시")
	class find {

		@Test
		@DisplayName("존재하지 않는 RT인 경우 예외가 발생한다.")
		void fail() {
			//given
			String refreshToken = "nonExistingToken";

			//when
			when(refreshTokenRepository.findRefreshTokenByToken(refreshToken))
				.thenReturn(Optional.empty());

			//then
			assertThrows(RefreshTokenNotFoundException.class, () -> {
				tokenService.getAccessTokensByRefreshToken(refreshToken);
			});

			verify(refreshTokenRepository, times(1)).findRefreshTokenByToken(refreshToken);
		}

		@Test
		@DisplayName("만료된 RT인 경우 예외가 발생한다.")
		void fail2() {
			//given
			String refreshToken = "expiredToken";
			RefreshToken token = RefreshToken.builder()
				.token(refreshToken)
				.userId(1L)
				.expiredAt(System.currentTimeMillis() - 1000)
				.build();

			//when
			when(refreshTokenRepository.findRefreshTokenByToken(refreshToken))
				.thenReturn(Optional.of(token));

			//then
			assertThrows(ExpiredTokenException.class, () -> {
				tokenService.getAccessTokensByRefreshToken(refreshToken);
			});

			verify(refreshTokenRepository, times(1)).findRefreshTokenByToken(refreshToken);
		}

		@Test
		@DisplayName("정상적인 RT가 존재하는 경우 AT를 성공적으로 갱신한다.")
		void renew() {
			//given
			String refreshToken = "validToken";
			Long userId = 1L;
			String accessToken = "newAccessToken";

			RefreshToken token = RefreshToken.builder()
				.token(refreshToken)
				.userId(userId)
				.expiredAt(System.currentTimeMillis() + 1000 * 60 * 60)
				.build();

			//when
			when(refreshTokenRepository.findRefreshTokenByToken(refreshToken))
				.thenReturn(Optional.of(token));
			when(jwtTokenProvider.getAccessToken(userId)).thenReturn(accessToken);

			Tokens tokens = tokenService.getAccessTokensByRefreshToken(refreshToken);

			//then
			assertNotNull(tokens.accessToken());
			assertNotNull(tokens.refreshToken());

			verify(refreshTokenRepository, times(1)).findRefreshTokenByToken(refreshToken);
			verify(jwtTokenProvider, times(1)).getAccessToken(userId);
		}

		@Test
		@DisplayName("RT의 만료기간이 일주일 이내로 남은 경우, RT를 갱신한다.")
		void renew2() {
			//given
			String refreshToken = "validToken";
			Long userId = 1L;
			String accessToken = "accessToken";

			long currentTime = System.currentTimeMillis();
			RefreshToken token = RefreshToken.builder()
				.token(refreshToken)
				.userId(userId)
				.expiredAt(currentTime + ONE_WEEK_IN_MILLI_SECONDS - 1000)
				.build();

			//when
			when(refreshTokenRepository.findRefreshTokenByToken(refreshToken))
				.thenReturn(Optional.of(token));
			when(jwtTokenProvider.getAccessToken(userId)).thenReturn(accessToken);

			Tokens tokens = tokenService.getAccessTokensByRefreshToken(refreshToken);

			//then
			assertNotNull(tokens.accessToken());
			assertNotNull(tokens.refreshToken());

			verify(refreshTokenRepository, times(1)).findRefreshTokenByToken(refreshToken);
			verify(jwtTokenProvider, times(1)).getAccessToken(userId);
			verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
		}
	}
}

