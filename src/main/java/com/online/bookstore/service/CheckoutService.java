package com.online.bookstore.service;

import com.online.bookstore.dto.response.CheckoutResponse;

public interface CheckoutService {

    CheckoutResponse checkout(String promotionCode);
}
