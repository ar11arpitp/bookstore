package com.online.bookstore.service.impl;

import com.online.bookstore.dto.request.CartItemRequest;
import com.online.bookstore.dto.response.BookResponse;
import com.online.bookstore.dto.response.CartItemResponse;
import com.online.bookstore.entity.BookEntity;
import com.online.bookstore.entity.CartItem;
import com.online.bookstore.entity.ShoppingCart;
import com.online.bookstore.entity.User;
import com.online.bookstore.exception.handler.NotFoundCustomException;
import com.online.bookstore.repository.CartItemRepository;
import com.online.bookstore.service.BookService;
import com.online.bookstore.service.CartItemService;
import com.online.bookstore.service.ShoppingCartService;
import com.online.bookstore.service.UserService;
import com.online.bookstore.common.enums.ErrorCodeEnums;
import com.online.bookstore.common.enums.SuccessCodeEnums;
import com.online.bookstore.common.util.CalculationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final ShoppingCartService shoppingCartService;
    private final CartItemRepository cartItemRepository;
    private final CalculationUtils calculationUtils;
    private final BookService bookService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    /**
     * Adds a book to the shopping cart.
     *
     * @param cartItemRequest The cart item request containing book ID and quantity.
     * @return A success message indicating the book was added to the cart.
     */
    @Override
    public String addBookToCart(CartItemRequest cartItemRequest) {
        BookEntity book = getBookById(cartItemRequest.getBookId());
        ShoppingCart shoppingCart = retrieveShoppingCartForCurrentLoginUser();
        Integer quantity = cartItemRequest.getQuantity();
        addItemToCart(book, shoppingCart, quantity);
        return SuccessCodeEnums.ADD_TO_CART_SUCCESS.getMessage();
    }

    /**
     * Removes a book from the shopping cart.
     *
     * @param bookId   The ID of the book to be removed.
     * @param quantity The quantity of the book to be removed. If null, the entire book is removed.
     * @return A success message indicating the book was removed from the cart.
     * @throws NotFoundCustomException If the book is not found in the cart.
     */
    @Override
    public String removeBookFromCart(Long bookId, Integer quantity) {
        BookEntity book = getBookById(bookId);
        ShoppingCart shoppingCart = retrieveShoppingCartForCurrentLoginUser();
        if (Objects.nonNull(quantity)) {
            removeBookFromCartImpl(book, shoppingCart, quantity);
            return SuccessCodeEnums.DELETED_CART_ITEM_SUCCESS.getMessage();
        } else {
            Optional<CartItem> cartItemOptional = cartItemRepository.findByShoppingCartAndBook(shoppingCart, book);
            if (cartItemOptional.isPresent()) {
                CartItem cartItem = cartItemOptional.get();
                cartItemRepository.delete(cartItem);
                return String.format(SuccessCodeEnums.DELETED_SUCCESS.getMessage(), cartItem.getId());
            } else {
                log.info("Book with ID {} does not exist in the cart", bookId);
                throw new NotFoundCustomException(ErrorCodeEnums.NOT_FOUND_ERR.getErrorCode(), ErrorCodeEnums.VALID_CART_NOT_FOUND.getMessage());
            }
        }
    }

    /**
     * Updates the quantity of a cart item.
     *
     * @param cartItemId  The ID of the cart item to be updated.
     * @param newQuantity The new quantity to be set.
     * @return The updated cart item response.
     */
    @Override
    public CartItemResponse updateCartItemQuantity(Long cartItemId, Integer newQuantity) {
        CartItem cartItem = findById(cartItemId);
        cartItem.setQuantity(newQuantity);
        cartItemRepository.save(cartItem);
        return CartItemResponse.builder()
                .book(modelMapper.map(cartItem.getBook(), BookResponse.class))
                .quantity(cartItem.getQuantity())
                .build();
    }

    private void addItemToCart(BookEntity book, ShoppingCart cart, int quantity) {
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getBook().getId().equals(book.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            calculationUtils.updateCartItemPrice(cartItem);
        } else {
            CartItem newItem = CartItem.builder()
                    .book(book)
                    .shoppingCart(cart)
                    .quantity(quantity)
                    .build();
            cart.getItems().add(newItem);
            calculationUtils.updateCartItemPrice(newItem);
        }
        calculationUtils.updateCartPrices(cart);
        shoppingCartService.update(cart);
    }

    private void removeBookFromCartImpl(BookEntity book, ShoppingCart shoppingCart, int quantity) {
        Optional<CartItem> existingItem = shoppingCart.getItems().stream()
                .filter(item -> item.getBook().equals(book))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            if (quantity >= cartItem.getQuantity()) {
                shoppingCart.getItems().remove(cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() - quantity);
                cartItem.setTotalPrice(BigDecimal.valueOf(cartItem.getBook().getPrice())
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            }
        }
        calculationUtils.updateCartPrices(shoppingCart);
        shoppingCartService.update(shoppingCart);
    }

    private User retrieveCurrentLoginUser() {
        User user = userService.retrieveCurrentLoginUser();
        log.info("Username: {}", user.getUsername());
        return user;
    }

    private ShoppingCart retrieveShoppingCartForCurrentLoginUser() {
        User user = retrieveCurrentLoginUser();
        ShoppingCart shoppingCart = shoppingCartService.retrieveActiveCartForUser(user);
        log.info("ShoppingCart ID: {}", shoppingCart.getId());
        return shoppingCart;
    }

    private BookEntity getBookById(Long id) {
        BookEntity book = bookService.findById(id);
        log.info("Book ID: {}", id);
        return book;
    }

    private CartItem findById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundCustomException(
                        ErrorCodeEnums.NOT_FOUND_ERR.getErrorCode(),
                        String.format(ErrorCodeEnums.NOT_FOUND_ERR.getMessage(), cartItemId)));
    }
}
