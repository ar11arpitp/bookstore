
package com.online.bookstore.enums;

import lombok.Getter;

@Getter
public enum CartStatusEnums {

    DRAFT("Draft"),
    PENDING("Pending"),
    COMPLETE("Complete"),
    INVALID("Invalid"),
    CANCELLED("Cancelled"),
    ON_HOLD("OnHold"),
    EXPIRED("Expired"),
    ERROR("Error");

    private final String displayName;

    CartStatusEnums(String displayName) {
        this.displayName = displayName;
    }
}

