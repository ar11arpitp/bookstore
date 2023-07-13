package com.online.bookstore.service.impl;

import com.online.bookstore.entity.BookEntity;
import com.online.bookstore.entity.ShoppingCart;
import com.online.bookstore.service.CheckoutService;
import com.online.bookstore.service.DiscountFactory;
import com.online.bookstore.service.ShoppingCartService;
import com.online.bookstore.dto.response.CheckoutResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final ShoppingCartService shoppingCartService;
    private final DiscountFactory discountFactory;

    /**
     * Performs the checkout process for the shopping cart.
     *
     * @param promotionCode The promotion code to be applied during checkout.
     * @return The checkout response containing the total price, total discount, and price after discount.
     */
    @Override
    public CheckoutResponse checkout(String promotionCode) {
        ShoppingCart shoppingCart = shoppingCartService.getUserCartEntity();
        BigDecimal totalPrice = shoppingCart.getTotalPrice();
        AtomicReference<BigDecimal> itemDiscount = new AtomicReference<>(BigDecimal.ZERO);
        BigDecimal totalPriceAfterDiscount = shoppingCart.getItems().stream()
                .map(item -> {
                    BookEntity book = item.getBook();
                    BigDecimal itemPrice = item.getTotalPrice();
                    itemDiscount.set(discountFactory.discountedPrice(book.getType(), itemPrice,promotionCode));
                    return BigDecimal.valueOf(item.getQuantity() * book.getPrice()).subtract(itemDiscount.get());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CheckoutResponse(totalPrice, itemDiscount.get(), totalPriceAfterDiscount);
    }
}
