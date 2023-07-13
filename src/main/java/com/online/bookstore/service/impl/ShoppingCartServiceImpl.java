package com.online.bookstore.service.impl;

import com.online.bookstore.entity.ShoppingCart;
import com.online.bookstore.entity.User;
import com.online.bookstore.enums.CartStatusEnums;
import com.online.bookstore.exception.handler.NotFoundCustomException;
import com.online.bookstore.repository.ShoppingCartRepository;
import com.online.bookstore.service.ShoppingCartService;
import com.online.bookstore.service.UserService;
import com.online.bookstore.dto.response.ShoppingCartResponse;
import com.online.bookstore.common.enums.ErrorCodeEnums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    /**
     * Creates a new shopping cart for the current user.
     *
     * @return The created shopping cart response.
     */
    @Override
    public ShoppingCartResponse createCart() {
        return modelMapper.map(shoppingCartRepository.save(ShoppingCart.builder()
                .cartStatusEnums(CartStatusEnums.DRAFT)
                .user(retrieveCurrentLoginUser()).active(Boolean.TRUE).build()), ShoppingCartResponse.class);
    }

    /**
     * Retrieves the shopping cart for the current user.
     *
     * @return The shopping cart response.
     */
    @Override
    public ShoppingCartResponse getUserCart() {
        return modelMapper.map(retrieveActiveCartForUser(retrieveCurrentLoginUser()), ShoppingCartResponse.class);
    }

    /**
     * Retrieves the shopping cart entity for the current user.
     *
     * @return The shopping cart entity.
     */
    @Override
    public ShoppingCart getUserCartEntity() {
        return retrieveActiveCartForUser(retrieveCurrentLoginUser());
    }

    /**
     * Retrieves the active shopping cart for the given user.
     *
     * @param user The user for whom to retrieve the cart.
     * @return The active shopping cart entity.
     * @throws NotFoundCustomException If the user does not have a valid cart.
     */
    @Override
    public ShoppingCart retrieveActiveCartForUser(User user) {
        return this.shoppingCartRepository.findByUserAndActiveIsTrue(user).orElseThrow(() -> {
            log.info("The user '{}' does not have a valid cart.", user.getUsername());
            return new NotFoundCustomException(ErrorCodeEnums.VALID_CART_NOT_FOUND.getMessage(), ErrorCodeEnums.VALID_CART_NOT_FOUND.getErrorCode());
        });
    }

    /**
     * Updates the shopping cart.
     *
     * @param shoppingCart The shopping cart to be updated.
     */
    @Override
    public void update(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
    }

    private User retrieveCurrentLoginUser() {
        User user = userService.retrieveCurrentLoginUser();
        log.info("Username: {}", user.getUsername());
        return user;
    }
}
