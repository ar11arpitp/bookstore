package com.online.bookstore.common.util;

import com.online.bookstore.entity.BookEntity;
import com.online.bookstore.entity.CartItem;
import com.online.bookstore.entity.ShoppingCart;
import com.online.bookstore.service.DiscountFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CalculationUtils {

    private final DiscountFactory discountFactory;

    /**
     * Updates the total price of a cart item based on its book price and quantity.
     *
     * @param item the cart item to update
     */
    public void updateCartItemPrice(CartItem item) {
        BigDecimal itemPrice = calculateItemPrice(BigDecimal.valueOf(item.getBook().getPrice()), item.getQuantity());
        item.setTotalPrice(itemPrice);
    }

    /**
     * Updates the total price, discount amount, and discounted price of a shopping cart.
     *
     * @param cart the shopping cart to update
     */
    public void updateCartPrices(ShoppingCart cart) {
        BigDecimal totalPrice = calculateTotalPrice(cart);
        cart.setTotalPrice(totalPrice);

        BigDecimal discountAmount = calculateDiscountAmount(cart);
        cart.setDiscountAmount(discountAmount);
        cart.setDiscountedPrice(totalPrice.subtract(discountAmount));
    }

    /**
     * Calculates the total price of a cart item based on its book price and quantity.
     *
     * @param bookPrice  the price of the book
     * @param quantity   the quantity of the book
     * @return the total price of the cart item
     */
    private BigDecimal calculateItemPrice(BigDecimal bookPrice, int quantity) {
        BigDecimal itemPrice = bookPrice.multiply(BigDecimal.valueOf(quantity));
        return itemPrice;
    }

    /**
     * Calculates the total price of a shopping cart by summing up the prices of all cart items.
     *
     * @param cart the shopping cart
     * @return the total price of the shopping cart
     */
    private BigDecimal calculateTotalPrice(ShoppingCart cart) {
        BigDecimal totalPrice = cart.getItems().stream()
                .map(item -> calculateItemPrice(BigDecimal.valueOf(item.getBook().getPrice()), item.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalPrice;
    }

    /**
     * Calculates the total discount amount for a shopping cart based on the discounted prices of its items.
     *
     * @param cart the shopping cart
     * @return the total discount amount for the shopping cart
     */
    private BigDecimal calculateDiscountAmount(ShoppingCart cart) {
        BigDecimal discountAmount = cart.getItems().stream()
                .map(item -> {
                    BookEntity book = item.getBook();
                    // Get discounted price based on book type using DiscountFactory
                    return discountFactory.discountedPrice(book.getType(), item.getTotalPrice());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return discountAmount;
    }
}
