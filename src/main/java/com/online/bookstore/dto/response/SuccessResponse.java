package com.online.bookstore.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Success Response")
public class SuccessResponse<T> extends GenericResponse {

    @Schema(description = "The success data")
    private T data;

    @Override
    public Boolean isSuccess() {
        return Boolean.TRUE;
    }
}

