package com.online.bookstore.service;

import com.online.bookstore.dto.request.CartItemRequest;
import com.online.bookstore.dto.response.CartItemResponse;

public interface CartItemService {

    String addBookToCart(CartItemRequest cartItemRequest);

    String removeBookFromCart(Long bookId, Integer quantity);

    CartItemResponse updateCartItemQuantity(Long cartItemId, Integer newQuantity);
}
