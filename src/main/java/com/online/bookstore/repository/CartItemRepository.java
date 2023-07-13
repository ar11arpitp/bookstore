package com.online.bookstore.repository;

import com.online.bookstore.entity.BookEntity;
import com.online.bookstore.entity.CartItem;
import com.online.bookstore.entity.ShoppingCart;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends org.springframework.data.jpa.repository.JpaRepository<CartItem, Long> {

    Optional<CartItem> findByShoppingCartAndBook(ShoppingCart shoppingCart, BookEntity book);
}
