package com.ppn.mock.backendmockppn.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{
    private final Object[] args;

    private final String messageName;

    public BadRequestException(String messageName, Object[] args){
        super(messageName);
        this.messageName = messageName;
        this.args = args;
    }
}
