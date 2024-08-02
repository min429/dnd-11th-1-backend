package com.dnd.accompany.domain.auth.oauth.exception;

import com.dnd.accompany.global.common.exception.BusinessException;
import com.dnd.accompany.global.common.response.ErrorCode;

public class HttpClientException extends BusinessException {
	public HttpClientException(ErrorCode errorCode) {
		super(errorCode);
	}
}
