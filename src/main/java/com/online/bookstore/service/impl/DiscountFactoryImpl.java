package com.online.bookstore.service.impl;

import com.online.bookstore.service.DiscountFactory;
import com.online.bookstore.common.config.DiscountConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class DiscountFactoryImpl implements DiscountFactory {

    private final DiscountConfig discountProperties;

    /**
     * Calculates the discounted price based on the book type.
     *
     * @param bookType The type of the book.
     * @param price    The original price of the book.
     * @return The discounted price.
     */
    @Override
    public BigDecimal discountedPrice(String bookType, BigDecimal price) {
        Predicate<String> isDiscountZero = discount -> List.of("0", "0.0").contains(discount);
        return switch (bookType) {
            case "fiction" -> !isDiscountZero.test(discountProperties.getFiction()) ?
                    NumberUtils.parseNumber(discountProperties.getFiction(), BigDecimal.class)
                            .divide(BigDecimal.valueOf(100))
                            .multiply(price) : price;

            case "comics" -> !isDiscountZero.test(discountProperties.getComics()) ?
                    NumberUtils.parseNumber(discountProperties.getComics(), BigDecimal.class)
                            .divide(BigDecimal.valueOf(100))
                            .multiply(price) : price;

            default -> price;
        };
    }

    @Override
    public BigDecimal discountedPrice(String bookType, BigDecimal price, String discountPer) {
        Predicate<String> isDiscountZero = discount -> List.of("0", "0.0").contains(discountPer);
        return switch (bookType) {
            case "fiction" ,"comics"-> !isDiscountZero.test(discountPer) ?
                    NumberUtils.parseNumber(discountPer, BigDecimal.class)
                            .divide(BigDecimal.valueOf(100))
                            .multiply(price) : price;

            default -> price;
        };
    }
}
