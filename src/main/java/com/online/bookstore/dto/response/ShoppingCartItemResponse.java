package com.online.bookstore.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Shopping cart item response")
public class ShoppingCartItemResponse extends BaseResponse<Long> {

    @Schema(description = "Book response")
    private BookResponse book;

    @Schema(description = "Quantity of the book in the shopping cart")
    private Integer quantity;
}

