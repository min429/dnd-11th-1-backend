package com.dnd.accompany.domain.auth.oauth.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dnd.accompany.domain.auth.oauth.dto.LoginRequest;
import com.dnd.accompany.domain.auth.oauth.dto.OAuthUserDataRequest;
import com.dnd.accompany.domain.auth.oauth.dto.OAuthUserDataResponse;
import com.dnd.accompany.domain.auth.oauth.handler.OAuthAuthenticationHandler;

@Service
public class OAuthService {

	private final Map<OAuthProvider, OAuthAuthenticationHandler> oAuthAuthenticationHandlers;

	public OAuthService(List<OAuthAuthenticationHandler> oAuthAuthenticationHandlers) {
		this.oAuthAuthenticationHandlers = oAuthAuthenticationHandlers.stream().collect(
			Collectors.toConcurrentMap(OAuthAuthenticationHandler::getAuthProvider, Function.identity())
		);
	}

	public OAuthUserDataResponse login(LoginRequest loginRequest) {
		OAuthProvider oAuthProvider = OAuthProvider.get(loginRequest.provider());

		OAuthAuthenticationHandler oAuthHandler = this.oAuthAuthenticationHandlers.get(oAuthProvider);

		OAuthUserDataRequest request = new OAuthUserDataRequest(
			loginRequest.accessToken()
		);

		return oAuthHandler.getOAuthUserData(request);
	}

	public void revoke() {
		// 회원 탈퇴 구현 시 추가합니다.
	}
}
