package com.online.bookstore.controller;

import com.online.bookstore.dto.request.CartItemRequest;
import com.online.bookstore.dto.response.BookResponse;
import com.online.bookstore.dto.response.CartItemResponse;
import com.online.bookstore.dto.response.SuccessResponse;
import com.online.bookstore.service.CartItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartItemService cartItemService;
    @InjectMocks
    private CartController cartItemController;
    private CartItemRequest request;
    private CartItemResponse cartItemResponse;

    @BeforeEach
    void setup() {
        request = new CartItemRequest();
        request.setQuantity(2);
        request.setBookId(2L);

        cartItemResponse = CartItemResponse.builder()
                .id(1L)
                .book(BookResponse.builder()
                        .id(1L)
                        .author("J.D. Salinger")
                        .price(9.99)
                        .build())
                .quantity(2)
                .totalPrice(BigDecimal.valueOf(19.98))
                .build();

    }

    @Test
    void testAddBookToCart() {
        String expectedData = "Added book to cart.";
        when(cartItemService.addBookToCart(request)).thenReturn(expectedData);
        ResponseEntity<SuccessResponse<String>> response = cartItemController.addBookToCart(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getData()).isEqualTo(expectedData);
    }

    @Test
    void givenBookId_whenRemoveBookFromCart_thenSuccess() {
        Long bookId = 1L;
        Integer quantity = 2;
        String expectedResult = "Book removed from cart.";

        when(cartItemService.removeBookFromCart(bookId, quantity)).thenReturn(expectedResult);

        ResponseEntity<SuccessResponse<String>> response = cartItemController.removeBookFromCart(bookId, quantity);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getData()).isEqualTo(expectedResult);
    }

    @Test
    void givenCartItemDetails_whenUpdateCartItemQuantity_thenSuccess() {
        Long cartItemId = 1L;
        Integer newQuantity = 2;

        when(cartItemService.updateCartItemQuantity(cartItemId, newQuantity)).thenReturn(cartItemResponse);

        ResponseEntity<SuccessResponse<CartItemResponse>> response = cartItemController.updateCartItemQuantity(cartItemId, newQuantity);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getData()).isEqualTo(cartItemResponse);
    }
}
