package com.dnd.accompany.domain.auth.exception;

import com.dnd.accompany.global.common.response.ErrorCode;

public class InvalidTokenException extends TokenException {
	public InvalidTokenException(ErrorCode errorCode) {
		super(errorCode);
	}
}
