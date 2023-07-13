package com.online.bookstore.service;

import java.math.BigDecimal;

public interface DiscountFactory {
    BigDecimal discountedPrice(String bookType, BigDecimal price, String promoCode);
    BigDecimal discountedPrice(String bookType, BigDecimal price);
}
