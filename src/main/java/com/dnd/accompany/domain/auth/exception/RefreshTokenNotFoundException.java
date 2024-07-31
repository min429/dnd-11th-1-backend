package com.dnd.accompany.domain.auth.exception;

import com.dnd.accompany.global.common.response.ErrorCode;

public class RefreshTokenNotFoundException extends TokenException {
	public RefreshTokenNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
