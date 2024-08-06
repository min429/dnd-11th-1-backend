package com.dnd.accompany.domain.user.exception;

import com.dnd.accompany.global.common.exception.BusinessException;
import com.dnd.accompany.global.common.response.ErrorCode;

public class UserProfileAlreadyExistsException extends BusinessException {
    public UserProfileAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
