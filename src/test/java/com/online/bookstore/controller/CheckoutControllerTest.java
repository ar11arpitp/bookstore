package com.online.bookstore.controller;

import com.online.bookstore.dto.response.CheckoutResponse;
import com.online.bookstore.dto.response.SuccessResponse;
import com.online.bookstore.service.CheckoutService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Objects;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckoutControllerTest {

    @Mock
    private CheckoutService checkoutService;

    @InjectMocks
    private CheckoutController checkoutController;

    @Test
    void givenNoPromoCode_whenCheckout_thenSuccess() {
        CheckoutResponse expectedResult = new CheckoutResponse(BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ZERO);

        when(checkoutService.checkout(null)).thenReturn(expectedResult);

        ResponseEntity<SuccessResponse<CheckoutResponse>> response = checkoutController.checkout(null);

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(Objects.requireNonNull(response.getBody()).getData()).isEqualTo(expectedResult);
    }

    @Test
    void givenPromoCode_whenCheckout_thenSuccess() {
        String promoCode = "TEST_PROMO_CODE";
        CheckoutResponse expectedResult = new CheckoutResponse(BigDecimal.TEN, BigDecimal.ONE, BigDecimal.TEN);

        when(checkoutService.checkout(promoCode)).thenReturn(expectedResult);

        ResponseEntity<SuccessResponse<CheckoutResponse>> response = checkoutController.checkout(promoCode);

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(Objects.requireNonNull(response.getBody()).getData()).isEqualTo(expectedResult);
    }

}
