package com.online.bookstore.exception.handler;

import java.io.Serial;
public class CustomDataException extends CustomException {

    @Serial
    private static final long serialVersionUID = 7168555867098356326L;

    public CustomDataException(String message, String errorCode) {
        super(message, errorCode);
    }
}
