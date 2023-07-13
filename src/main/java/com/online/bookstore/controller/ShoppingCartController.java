package com.online.bookstore.controller;

import com.online.bookstore.dto.response.ShoppingCartResponse;
import com.online.bookstore.dto.response.SuccessResponse;
import com.online.bookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling shopping cart operations.
 */
@Validated
@RestController
@Tag(name = "ShoppingCart Controller", description = "APIs for handling shopping cart operations")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    /**
     * Create a new shopping cart.
     *
     * @return ResponseEntity containing a success response with the created cart.
     */
    @PostMapping("/createCart")
    @Operation(summary = "Create a new shopping cart", security = {@SecurityRequirement(name = "basicAuth")})
    @ApiResponse(responseCode = "201", description = "Cart created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    public ResponseEntity<SuccessResponse<ShoppingCartResponse>> createCart() {
        ShoppingCartResponse cartResponse = shoppingCartService.createCart();

        SuccessResponse<ShoppingCartResponse> response = SuccessResponse.<ShoppingCartResponse>builder()
                .message("Cart created successfully")
                .data(cartResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Get the shopping cart for the current user.
     *
     * @return ResponseEntity containing a success response with the user's cart.
     */
    @GetMapping("/getCartDetails")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    @Operation(summary = "Get the shopping cart for the current user", security = {@SecurityRequirement(name = "basicAuth")})
    public ResponseEntity<SuccessResponse<ShoppingCartResponse>> getUserCart() {
        ShoppingCartResponse userCart = shoppingCartService.getUserCart();

        SuccessResponse<ShoppingCartResponse> response = SuccessResponse.<ShoppingCartResponse>builder()
                .data(userCart)
                .build();

        return ResponseEntity.ok(response);
    }
}

