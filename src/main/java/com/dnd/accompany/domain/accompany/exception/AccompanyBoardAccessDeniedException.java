package com.dnd.accompany.domain.accompany.exception;

import com.dnd.accompany.global.common.response.ErrorCode;

public class AccompanyBoardAccessDeniedException extends AccompanyBoardException {
	public AccompanyBoardAccessDeniedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
