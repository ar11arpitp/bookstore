package com.online.bookstore.exception.handler;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6008437565530199228L;

    private final String errorCode;

    public CustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
