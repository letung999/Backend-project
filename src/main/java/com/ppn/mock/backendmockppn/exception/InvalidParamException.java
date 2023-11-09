package com.ppn.mock.backendmockppn.exception;

import com.ppn.mock.backendmockppn.constant.MessageStatus;
import lombok.Getter;

@Getter
public class InvalidParamException extends BadRequestException {
    public InvalidParamException(String param) {
        super(MessageStatus.ERR_MSG_INVALID_DATA, new Object[]{param});
    }
}
