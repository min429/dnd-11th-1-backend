package com.dnd.accompany.domain.user.exception;

import com.dnd.accompany.global.common.exception.BusinessException;
import com.dnd.accompany.global.common.response.ErrorCode;

public class UserProfileNotFoundException extends BusinessException {
	public UserProfileNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
