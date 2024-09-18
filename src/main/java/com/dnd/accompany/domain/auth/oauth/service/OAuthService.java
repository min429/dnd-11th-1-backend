package com.dnd.accompany.domain.auth.oauth.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.dnd.accompany.domain.user.entity.User;
import com.dnd.accompany.domain.user.infrastructure.UserRepository;
import com.dnd.accompany.global.common.exception.NotFoundException;
import org.springframework.stereotype.Service;

import com.dnd.accompany.domain.auth.oauth.dto.LoginRequest;
import com.dnd.accompany.domain.auth.oauth.dto.OAuthUserDataRequest;
import com.dnd.accompany.domain.auth.oauth.dto.OAuthUserDataResponse;
import com.dnd.accompany.domain.auth.oauth.handler.OAuthAuthenticationHandler;

import static com.dnd.accompany.global.common.response.ErrorCode.USER_NOT_FOUND;

@Service
public class OAuthService {

	private final Map<OAuthProvider, OAuthAuthenticationHandler> oAuthAuthenticationHandlers;
	private final UserRepository userRepository;

	public OAuthService(List<OAuthAuthenticationHandler> oAuthAuthenticationHandlers, UserRepository userRepository) {
		this.oAuthAuthenticationHandlers = oAuthAuthenticationHandlers.stream().collect(
			Collectors.toConcurrentMap(OAuthAuthenticationHandler::getAuthProvider, Function.identity())
		);
        this.userRepository = userRepository;
    }

	public OAuthUserDataResponse login(LoginRequest loginRequest) {
		OAuthProvider oAuthProvider = OAuthProvider.get(loginRequest.provider());

		OAuthAuthenticationHandler oAuthHandler = this.oAuthAuthenticationHandlers.get(oAuthProvider);

		OAuthUserDataRequest request = new OAuthUserDataRequest(
			loginRequest.accessToken()
		);

		return oAuthHandler.getOAuthUserData(request);
	}

	public void revoke(Long userId) {
		User user = userRepository
				.findById(userId)
				.orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

		OAuthProvider oAuthProvider = OAuthProvider.get(user.getProvider());
		OAuthAuthenticationHandler oAuthHandler = this.oAuthAuthenticationHandlers.get(oAuthProvider);

		oAuthHandler.unlink(userId);
	}
}
