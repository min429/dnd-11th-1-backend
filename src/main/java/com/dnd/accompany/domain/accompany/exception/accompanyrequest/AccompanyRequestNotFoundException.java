package com.dnd.accompany.domain.accompany.exception.accompanyrequest;

import com.dnd.accompany.global.common.exception.BusinessException;
import com.dnd.accompany.global.common.response.ErrorCode;

public class AccompanyRequestNotFoundException extends BusinessException {
	public AccompanyRequestNotFoundException(ErrorCode errorCode) {super(errorCode); }
}
