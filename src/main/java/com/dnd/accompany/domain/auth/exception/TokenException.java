package com.dnd.accompany.domain.auth.exception;

import com.dnd.accompany.global.common.exception.BusinessException;
import com.dnd.accompany.global.common.response.ErrorCode;

public class TokenException extends BusinessException {
	public TokenException(ErrorCode errorCode) {
		super(errorCode);
	}
}
