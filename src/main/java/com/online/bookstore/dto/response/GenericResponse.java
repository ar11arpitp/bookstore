package com.online.bookstore.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(Include.NON_NULL)
@Schema(description = "The Abstract base response.")
public abstract class GenericResponse {

    @Schema(description = "Status", example = "true")
    private Boolean success;

    @Schema(description = "Description", example = "created successfully")
    private String message;

    @Schema(description = "Message code", example = "CREATED")
    private String msgCode;

    @Schema(description = "Additional Details", example = " 'arpitp@xyz.com' has been created")
    private String details;

    @Schema(description = "Endpoint", example = "/api/v1/cart")
    private String uriPath;

    public abstract Boolean isSuccess();

    protected GenericResponse(Boolean success, String message, String msgCode) {
        this.success = success;
        this.message = message;
        this.msgCode = msgCode;
    }
}
