package com.online.bookstore.service;

import com.online.bookstore.entity.ShoppingCart;
import com.online.bookstore.entity.User;
import com.online.bookstore.dto.response.ShoppingCartResponse;

public interface ShoppingCartService {

    ShoppingCartResponse createCart();

    ShoppingCartResponse getUserCart();

    ShoppingCart retrieveActiveCartForUser(User user);

    ShoppingCart getUserCartEntity();

    void update(ShoppingCart shoppingCart);
}
