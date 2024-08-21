package com.dnd.accompany.domain.accompany.exception.accompanyboard;

import com.dnd.accompany.global.common.exception.BusinessException;
import com.dnd.accompany.global.common.response.ErrorCode;

public class AccompanyBoardAccessDeniedException extends BusinessException {
	public AccompanyBoardAccessDeniedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
