package com.micro.faq.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String errorMsg) {
        super(errorMsg);
    }

    public ResourceNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
