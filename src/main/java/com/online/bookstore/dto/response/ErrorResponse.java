package com.online.bookstore.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Error response")
public final class ErrorResponse extends GenericResponse {

    @Schema(description = "Error code", example = "INVALID_REQUEST")
    private final String errorCode;

    @Override
    public Boolean isSuccess() {
        return Boolean.FALSE;
    }
}
