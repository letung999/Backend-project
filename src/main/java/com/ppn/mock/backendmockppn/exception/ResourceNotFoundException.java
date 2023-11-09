package com.ppn.mock.backendmockppn.exception;

import com.ppn.mock.backendmockppn.constant.MessageStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldValue) {
        super(String.format("%s" + MessageStatus.ERR_MSG_DATA_NOT_FOUND + " %s", resourceName, fieldValue));
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }
}
