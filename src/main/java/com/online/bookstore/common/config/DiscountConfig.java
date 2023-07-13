package com.online.bookstore.common.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@ConfigurationProperties(value = "application.discounts")
@Data
@Component
public class DiscountConfig {
    private String fiction;
    private String comics;
}
