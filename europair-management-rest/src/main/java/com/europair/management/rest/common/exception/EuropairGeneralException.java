package com.europair.management.rest.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class EuropairGeneralException extends RuntimeException {
    public EuropairGeneralException(String message, Exception e) {
        super(message, e);
    }
}
