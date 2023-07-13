package com.online.bookstore.exception.handler;

import com.online.bookstore.dto.response.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class ErrorResponseDescription<H extends Serializable, D> extends GenericResponse {

    private H errorHeader;
    private D errorDetails;

    @Override
    public Boolean isSuccess() {
        return Boolean.FALSE;
    }

    public ErrorResponseDescription(String message, String responseCode, H errorHeader, D errorDetails) {
        super(Boolean.FALSE, message, responseCode);
        this.errorHeader = errorHeader;
        this.errorDetails = errorDetails;
    }
}

