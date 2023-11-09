package com.ppn.mock.backendmockppn.exception.car;

import lombok.Getter;

@Getter
public class DeletedCarException extends RuntimeException {
    private final Object[] args;

    private final String messageName;

    public DeletedCarException(String messageName, Object[] args){
        super(messageName);
        this.messageName = messageName;
        this.args = args;
    }
}
