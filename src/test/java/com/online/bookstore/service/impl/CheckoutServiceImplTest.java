package com.online.bookstore.service.impl;

import com.online.bookstore.dto.response.CheckoutResponse;
import com.online.bookstore.entity.BookEntity;
import com.online.bookstore.entity.CartItem;
import com.online.bookstore.entity.ShoppingCart;
import com.online.bookstore.service.DiscountFactory;
import com.online.bookstore.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceImplTest {

    @Mock
    private ShoppingCartService mockShoppingCartService;
    @Mock
    private DiscountFactory mockDiscountFactory;

    private CheckoutServiceImpl checkoutServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        checkoutServiceImplUnderTest = new CheckoutServiceImpl(mockShoppingCartService, mockDiscountFactory);
    }

    @Test
    void testCheckout() {
        // Setup
        final CheckoutResponse expectedResult = new CheckoutResponse(new BigDecimal("0.00"), new BigDecimal("0.00"),
                new BigDecimal("0.00"));

        // Configure ShoppingCartService.getUserCartEntity(...).
        final ShoppingCart shoppingCart = ShoppingCart.builder()
                .items(List.of(CartItem.builder()
                        .book(BookEntity.builder()
                                .type("type")
                                .price(0.0)
                                .build())
                        .quantity(0)
                        .totalPrice(new BigDecimal("0.00"))
                        .build()))
                .totalPrice(new BigDecimal("0.00"))
                .build();
        when(mockShoppingCartService.getUserCartEntity()).thenReturn(shoppingCart);

        when(mockDiscountFactory.discountedPrice("type", new BigDecimal("0.00"))).thenReturn(new BigDecimal("0.00"));

        // Run the test
        final CheckoutResponse result = checkoutServiceImplUnderTest.checkout("promotionCode");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
