package com.dnd.accompany.domain.user.exception;

import com.dnd.accompany.global.common.exception.BusinessException;
import com.dnd.accompany.global.common.response.ErrorCode;

public class UserNotFoundException extends BusinessException {
	public UserNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
