package com.dnd.accompany.domain.auth.exception;

import com.dnd.accompany.global.common.response.ErrorCode;

public class ExpiredTokenException extends TokenException {
	public ExpiredTokenException(ErrorCode errorCode) {
		super(errorCode);
	}
}
