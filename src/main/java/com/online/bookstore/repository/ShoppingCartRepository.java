package com.online.bookstore.repository;

import com.online.bookstore.entity.ShoppingCart;
import com.online.bookstore.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends org.springframework.data.jpa.repository.JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByUserAndActiveIsTrue(User user);
}
