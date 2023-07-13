package com.online.bookstore.dto.request;

import com.online.bookstore.common.constants.AppConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "Request object for CartItem APIs")
public class CartItemRequest {

    @NotNull(message = "bookId " + AppConstants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "bookId " + AppConstants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "The ID of the book added to the cart.")
    private Long bookId;

    @NotNull(message = "quantity " + AppConstants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "quantity " + AppConstants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "The quantity of the book added to the cart.")
    private Integer quantity;
}