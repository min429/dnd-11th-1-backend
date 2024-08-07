package com.dnd.accompany.domain.accompany.exception;

import com.dnd.accompany.global.common.exception.BusinessException;
import com.dnd.accompany.global.common.response.ErrorCode;

public class AccompanyBoardException extends BusinessException {
	public AccompanyBoardException(ErrorCode errorCode) {
		super(errorCode);
	}
}
