package com.online.bookstore.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCodeEnums {
    NOT_FOUND_EXCEPTION("EXP-NOT_FOUND", "Not found exception"),
    NOT_FOUND_ERR("MSG-01", "ID: %s Does Not exist."),
    VALID_CART_NOT_FOUND("MSG-017", "Could Not find a valid cart"),
    INTERNAL_SERVER_ERROR("101", "Something went wrong"),
    HANDLER_NOT_FOUND("102", "No handler found"),
    METHOD_NOT_SUPPORTED("103", "Method not supported"),
    ERROR_WRITING_JSON("104", "Error in writing JSON"),
    ERROR_READING_JSON("105", "Error in reading JSON"),
    BIND_EXCEPTION("106", "Binding Exception Error"),
    REQUEST_PARAMETER_MISSING("107", "Missing request parameter"),
    INVALID_ARGUMENTS("108", "Invalid method arguments "),
    CONSTRAINT_VIOLATION("109", "Validation error"),
    MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION("ERR-110", "Please check size and try again"),
    SQL_EXCEPTION("ERR-111", "SQL Exception Occured"),
    DATA_INTEGRITY_VIOLATION_EXCEPTION("ERR-112", "Data Integrity Violation Exception"),
    CONSTRAINT_VIOLATION_EXCEPTION("ERR-113", "Constraint Violation Exception"),
    BAD_REQUEST("ERR-114", "Oops! Something went wrong"),
    ACCESS_DENIED("ERR-115", "Access denied."),
    CONFLICT("ERR-116", "Conflict"),
    NOT_FOUND("ERR-117", "Not found"),
    UNAUTHORIZED("ERR-118", "Unauthorized");

    final String errorCode;
    final String message;
}
