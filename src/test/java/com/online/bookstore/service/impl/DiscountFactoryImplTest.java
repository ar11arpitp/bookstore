package com.online.bookstore.service.impl;

import com.online.bookstore.common.config.DiscountConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscountFactoryImplTest {

    @Mock
    private DiscountConfig mockDiscountProperties;

    private DiscountFactoryImpl discountFactoryImplUnderTest;

    @BeforeEach
    void setUp() {
        //discountFactoryImplUnderTest = new DiscountFactoryImpl(mockDiscountProperties);
    }

    @Test
    void testDiscountedPrice() {
        // Setup
        //when(mockDiscountProperties.getFiction()).thenReturn("10");

        // Run the test
        final BigDecimal result = discountFactoryImplUnderTest.discountedPrice("fiction", new BigDecimal("10"),"10");

        // Verify the results
        assertThat(result).isEqualTo(new BigDecimal("1.0"));
    }

    @Test
    void testDiscountedPrice_comic() {
        // Setup
        when(mockDiscountProperties.getComics()).thenReturn("10");

        // Run the test
        final BigDecimal result = discountFactoryImplUnderTest.discountedPrice("comics", new BigDecimal("10"),"10");

        // Verify the results
        assertThat(result).isEqualTo(new BigDecimal("1.0"));
    }
}
