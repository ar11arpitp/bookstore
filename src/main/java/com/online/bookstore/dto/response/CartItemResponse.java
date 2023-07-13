package com.online.bookstore.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Response object for a single item in the cart")
public class CartItemResponse extends BaseResponse<Long> {

    @Schema(description = "The book associated with cart item")
    private BookResponse book;

    @Schema(description = "The number of the book in this cart item")
    private Integer quantity;

    @Schema(description = "The total price of the cart")
    private BigDecimal totalPrice;
}

