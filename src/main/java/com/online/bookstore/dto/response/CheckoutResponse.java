package com.online.bookstore.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Schema(description = "The response object for a checkout operation.")
public record CheckoutResponse(
        @Schema(description = "Cart Total proce") @Getter @Setter BigDecimal totalPrice,
        @Schema(description = "Discount applied to the cart") @Getter @Setter BigDecimal totalDiscountPrice,
        @Schema(description = "Final price after Discount applied") @Getter @Setter BigDecimal totalPriceAfterDiscount
) {
}
