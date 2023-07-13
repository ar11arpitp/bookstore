package com.online.bookstore.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GeneralErrorCodeEnums {

    GENERAL("General error"),
    CONNECTION_FAILED("Could not connect"),
    ITEM_NOT_FOUND("Item Not Found"),
    INVALID_ITEM("Item is not valid"),
    MALFORMED_JSON_REQUEST("Malformed JSON request"),
    ERROR_WRITING_JSON("Error writing JSON output."),
    VALIDATION_ERROR("Validation error"),
    SOMETHING_WENT_WRONG("Something went wrong."),
    REQUEST_PARAMETER_MISSING("Request parameter is missing"),
    HANDLER_NOT_FOUND("Handler not found"),
    SERVICE_TIMEOUT("Service timeout");

    final String details;
}
