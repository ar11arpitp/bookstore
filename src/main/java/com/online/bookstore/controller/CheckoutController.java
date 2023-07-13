package com.online.bookstore.controller;

import com.online.bookstore.dto.response.CheckoutResponse;
import com.online.bookstore.dto.response.SuccessResponse;
import com.online.bookstore.service.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling checkout operations.
 */
@RestController
@Tag(name = "Checkout Controller" ,description = "APIs for handling checkout operations.")
@RequestMapping("/v1/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    /**
     * Checkout shopping cart.
     *
     * @param promoCode The promotional code to apply (optional).
     * @return ResponseEntity containing a success response with the checkout response.
     */
    @PostMapping
    @Operation(
            summary = "Checkout shopping cart",
            security = {@SecurityRequirement(name = "basicAuth")},
            description = "Calculates the total payable amount for the user's shopping cart after applying any applicable discounts."
    )
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    public ResponseEntity<SuccessResponse<CheckoutResponse>> checkout(@RequestParam(required = false) String promoCode) {
        CheckoutResponse checkoutResponse = checkoutService.checkout(promoCode);

        SuccessResponse<CheckoutResponse> response = SuccessResponse.<CheckoutResponse>builder()
                .data(checkoutResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}
