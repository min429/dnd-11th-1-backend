package com.dnd.accompany.domain.auth.oauth.handler;

import com.dnd.accompany.domain.user.infrastructure.UserRepository;
import com.dnd.accompany.global.common.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.dnd.accompany.domain.auth.oauth.dto.KakaoUserData;
import com.dnd.accompany.domain.auth.oauth.dto.OAuthUserDataRequest;
import com.dnd.accompany.domain.auth.oauth.dto.OAuthUserDataResponse;
import com.dnd.accompany.domain.auth.oauth.exception.HttpClientException;
import com.dnd.accompany.domain.auth.oauth.service.OAuthProvider;
import com.dnd.accompany.global.common.response.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoService implements OAuthAuthenticationHandler {

	private final RestTemplate restTemplate;
	private final UserRepository userRepository;

	@Value("${spring.oauth2.kakao.host}")
	private String host;

	@Value("${spring.oauth2.kakao.admin-key}")
	private String adminKey;


	@Override
	public OAuthProvider getAuthProvider() {
		return OAuthProvider.KAKAO;
	}

	@Override
	public OAuthUserDataResponse getOAuthUserData(OAuthUserDataRequest request) {
		String url = host + "/v2/user/me";

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeaders.add("Authorization", "Bearer " + request.getAccessToken());

		HttpEntity<?> httpRequest = new HttpEntity<>(null, httpHeaders);

		try {
			ResponseEntity<KakaoUserData> response = restTemplate.exchange(
					url,
					HttpMethod.GET,
					httpRequest,
					KakaoUserData.class
			);
			assert response.getBody() != null;

			KakaoUserData userData = response.getBody();

			return OAuthUserDataResponse.builder()
					.provider(getAuthProvider().toString())
					.profileImageUrl(userData.getProfileImage())
					.oauthId(userData.getId().toString())
					.nickname(userData.getNickname())
					.build();

		} catch (Exception e) {
			log.warn("[KakaoService] failed to get OAuth User Data = {}", request.getAccessToken());

			if (e instanceof RestClientResponseException) {
				throw new HttpClientException(ErrorCode.INVALID_OAUTH_TOKEN);
			}

			throw new HttpClientException(ErrorCode.HTTP_CLIENT_REQUEST_FAILED);
		}
	}

	@Override
	public void unlink(Long userId) {
		String oauthId = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND))
				.getOauthId();

		String url = host + "/v1/user/unlink";

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeaders.add("Authorization", "KakaoAK " + adminKey);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("target_id_type", "user_id");
		body.add("target_id", oauthId);

		HttpEntity<?> httpRequest = new HttpEntity<>(body, httpHeaders);

		try {
			ResponseEntity<KakaoUserData> response = restTemplate.exchange(
					url,
					HttpMethod.POST,
					httpRequest,
					KakaoUserData.class
			);
			assert response.getBody() != null;

		} catch (RestClientException e) {
			log.warn("[KakaoService] failed to unlink User = {}", oauthId);

			if (e instanceof RestClientResponseException) {
				throw new HttpClientException(ErrorCode.INVALID_OAUTH_TOKEN);
			}

			throw new HttpClientException(ErrorCode.HTTP_CLIENT_REQUEST_FAILED);
		}
	}
}
