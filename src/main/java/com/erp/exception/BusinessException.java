package com.erp.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    public BusinessException (String msg, Throwable cause) {
        super(msg, cause);
    }

    public BusinessException (String msg) {
        super(msg);
    }
}
