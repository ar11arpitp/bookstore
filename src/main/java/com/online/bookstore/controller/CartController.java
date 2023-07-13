package com.online.bookstore.controller;

import com.online.bookstore.dto.request.CartItemRequest;
import com.online.bookstore.dto.response.CartItemResponse;
import com.online.bookstore.dto.response.SuccessResponse;
import com.online.bookstore.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="Cart Controller", description = "API operations for managing item in the cart")
@RequestMapping("/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;

    /**
     * Add a book to the cart.
     *
     * @param cartItemRequest The request containing the book details to be added to the cart.
     * @return ResponseEntity containing a success response with the added book message.
     */
    @Operation(summary = "Add a book to the cart", security = {@SecurityRequirement(name = "basicAuth")})
    @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    @PostMapping("/addToCart")
    public ResponseEntity<SuccessResponse<String>> addBookToCart(@RequestBody CartItemRequest cartItemRequest) {
        String addedBookMessage = cartItemService.addBookToCart(cartItemRequest);

        SuccessResponse<String> response = SuccessResponse.<String>builder()
                .data(addedBookMessage)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Remove a book from the cart.
     *
     * @param bookId   The ID of the book to be removed from the cart.
     * @param quantity The quantity of the book to be removed (optional).
     * @return ResponseEntity containing a success response with the removed book message.
     */
    @Operation(summary = "Remove a book from the cart", security = {@SecurityRequirement(name = "basicAuth")})
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    @DeleteMapping("deleteBookById/{bookId}")
    public ResponseEntity<SuccessResponse<String>> removeBookFromCart(
            @Parameter(name = "bookId", in = ParameterIn.PATH, required = true) @PathVariable Long bookId,
            @Parameter(name = "quantity", in = ParameterIn.QUERY) @RequestParam(required = false) Integer quantity) {
        String removedBookMessage = cartItemService.removeBookFromCart(bookId, quantity);

        SuccessResponse<String> response = SuccessResponse.<String>builder()
                .data(removedBookMessage)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * Update the number of books in the cart.
     *
     * @param cartItemId  The ID of the cart item to update.
     * @param newQuantity The new quantity of the books.
     * @return ResponseEntity containing a success response with the updated cart item.
     */
    @Operation(summary = "Update the number of books in the cart", security = {@SecurityRequirement(name = "basicAuth")})
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    @PutMapping("updateCart/{cartItemId}/{newQuantity}")
    public ResponseEntity<SuccessResponse<CartItemResponse>> updateCartItemQuantity(
            @Parameter(name = "cartItemId", in = ParameterIn.PATH, required = true) @PathVariable Long cartItemId,
            @Parameter(name = "newQuantity", in = ParameterIn.PATH, required = true) @PathVariable Integer newQuantity) {
        CartItemResponse updatedCartItem = cartItemService.updateCartItemQuantity(cartItemId, newQuantity);

        SuccessResponse<CartItemResponse> response = SuccessResponse.<CartItemResponse>builder()
                .data(updatedCartItem)
                .build();

        return ResponseEntity.ok(response);
    }
}


