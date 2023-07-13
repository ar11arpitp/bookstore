package com.online.bookstore.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "The base response object for all API responses.")
public class BaseResponse<ID extends Serializable> {

    @Schema(description = "The ID of the entity.")
    private ID id;

    @Schema(description = "Created By")
    private String createdBy;

    @Schema(description = "Date Of Creation.")
    private String creationDate;

    @Schema(description = "Last Modified By")
    private String lastModifiedBy;

    @Schema(description = "Last Modified Date ")
    private String lastModifiedDate;
}
