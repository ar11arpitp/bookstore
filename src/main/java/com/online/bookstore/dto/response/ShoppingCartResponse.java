package com.online.bookstore.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Shopping cart details")
public class ShoppingCartResponse extends BaseResponse<Long> {

    @Schema(description = "Total discount")
    private BigDecimal totalDiscountAmount;

    @Schema(description = "Sub total price")
    private BigDecimal subTotal;

    @Schema(description = "Tax amount")
    private BigDecimal tax;

    @Schema(description = "Total price")
    private BigDecimal totalPrice;

    @Schema(description = "Status of the cart")
    private String cartStatus;

    @Schema(description = "Cart Status")
    private Boolean active;

    @Schema(description = "Discount amount")
    private BigDecimal discountAmount;

    @Schema(description = "Discounted price")
    private BigDecimal discountedPrice;

    @Schema(description = "List of cart items")
    private List<CartItemResponse> cartItems;
}

