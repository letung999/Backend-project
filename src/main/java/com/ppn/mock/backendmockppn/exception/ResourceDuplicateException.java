package com.ppn.mock.backendmockppn.exception;

import com.ppn.mock.backendmockppn.constant.MessageStatus;

public class ResourceDuplicateException extends RuntimeException {
    private String resourceName;
    private String fieldValue;

    public ResourceDuplicateException(String resourceName, String fieldValue) {
        super(String.format("%s " + MessageStatus.ERR_MSG_DATA_DUPLICATED + ": %s", resourceName, fieldValue));
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }
}
