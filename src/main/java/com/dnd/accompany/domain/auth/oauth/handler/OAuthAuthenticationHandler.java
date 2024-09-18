package com.dnd.accompany.domain.auth.oauth.handler;

import com.dnd.accompany.domain.auth.oauth.dto.OAuthUserDataRequest;
import com.dnd.accompany.domain.auth.oauth.dto.OAuthUserDataResponse;
import com.dnd.accompany.domain.auth.oauth.service.OAuthProvider;

public interface OAuthAuthenticationHandler {
	OAuthProvider getAuthProvider();

	OAuthUserDataResponse getOAuthUserData(OAuthUserDataRequest request);

	void unlink(Long userId);
}
