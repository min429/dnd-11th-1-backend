package com.dnd.accompany.domain.accompany.exception;

import com.dnd.accompany.global.common.response.ErrorCode;

public class AccompanyBoardNotFoundException extends AccompanyBoardException {
	public AccompanyBoardNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
