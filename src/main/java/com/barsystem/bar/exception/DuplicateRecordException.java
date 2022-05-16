package com.barsystem.bar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateRecordException extends RuntimeException{
    public DuplicateRecordException(String message) {
        super(message);
    }

    public DuplicateRecordException(String message, Throwable cause) {
        super(message, cause);
    }
}
