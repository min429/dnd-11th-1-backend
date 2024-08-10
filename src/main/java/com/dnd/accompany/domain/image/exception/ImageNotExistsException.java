package com.dnd.accompany.domain.image.exception;

import com.dnd.accompany.global.common.exception.BusinessException;
import com.dnd.accompany.global.common.response.ErrorCode;

public class ImageNotExistsException extends BusinessException {
    public ImageNotExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
