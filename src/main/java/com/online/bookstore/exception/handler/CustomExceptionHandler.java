package com.online.bookstore.exception.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.online.bookstore.common.enums.ErrorCodeEnums;
import com.online.bookstore.dto.response.GenericResponse;
import com.online.bookstore.dto.response.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundCustomException.class, EntityNotFoundException.class})
    public ResponseEntity<GenericResponse> handle(NotFoundCustomException exception) {
        return this.createGlobalException(exception.getMessage(), exception.getErrorCode(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerCustomException.class)
    public ResponseEntity<GenericResponse> handleInternalServerException(InternalServerCustomException exception) {
        return this.createGlobalException(exception.getMessage(), exception.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomDataException.class)
    public ResponseEntity<GenericResponse> handleBadRequestException(CustomDataException exception) {
        return this.createGlobalException(exception.getMessage(), exception.getErrorCode(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GenericResponse> handle(AccessDeniedException accessDeniedException) {
        return unauthorizedResponse();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<GenericResponse> handle(ConstraintViolationException exception) {
        return this.createGlobalException(
                exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining()),
                ErrorCodeEnums.BAD_REQUEST.getErrorCode(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<GenericResponse> handle(MissingRequestHeaderException exception, HttpStatus status) {
        return this.createGlobalException(
                exception.getHeaderName() + " parameter is missing", ErrorCodeEnums.REQUEST_PARAMETER_MISSING.getErrorCode(), status);
    }

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<GenericResponse> handle(MaxUploadSizeExceededException maxUploadSizeExceededException) {
        return this.createGlobalException(maxUploadSizeExceededException.getMessage(),
                ErrorCodeEnums.MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION.getErrorCode(), HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResponseEntity<GenericResponse> handle(SQLException exception) {
        return this.createGlobalException(exception.getMessage(),
                ErrorCodeEnums.SQL_EXCEPTION.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<GenericResponse> handle(NoSuchElementException exception) {
        return this.createGlobalException(
                exception.getMessage(), ErrorCodeEnums.NOT_FOUND.getErrorCode(), HttpStatus.NOT_FOUND);
    }

    /**
     * @param exception DuplicateKeyException
     * @return BaseResponse base response
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<GenericResponse> handle(DuplicateKeyException exception) {
        return this.createGlobalException(
                exception.getMessage(), ErrorCodeEnums.CONFLICT.getErrorCode(), HttpStatus.CONFLICT);
    }

    /**
     * @param exception IllegalArgumentException
     * @return BaseResponse base response
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GenericResponse> handle(IllegalArgumentException exception) {
        return this.createGlobalException(
                exception.getMessage(), ErrorCodeEnums.BAD_REQUEST.getErrorCode(), HttpStatus.BAD_REQUEST);
    }

    /**
     * @param exception ValidationException
     * @return BaseResponse base response
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<GenericResponse> handle(ValidationException exception) {
        return this.createGlobalException(
                exception.getMessage(), ErrorCodeEnums.BAD_REQUEST.getErrorCode(), HttpStatus.BAD_REQUEST);
    }

    /**
     * @param exception InvalidFormatException
     * @return BaseResponse base response
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<GenericResponse> handle(InvalidFormatException exception) {
        log.debug(exception.toString(), exception);
        log.error("InvalidFormatException message {}, {}", exception.getMessage(), exception.getStackTrace());
        List<String> errorsList = new ArrayList<>();
        Class<?> type = exception.getTargetType();
        if (type != null) {
            return new ResponseEntity<>(
                    new ErrorResponseDescription<>("Invalid request", HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                            exception.getMessage(), Collections.singletonList(
                            "The parameter of '" + type.getSimpleName() + " must have a value among:"
                                    + StringUtils.join(type.getEnumConstants(), ", "))), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        exception.getPath().forEach(reference -> errorsList.add("Value of '" + reference.getFieldName() + " not a valid representation"));
        return new ResponseEntity<>(
                new ErrorResponseDescription<>("Invalid request", HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                        exception.getMessage(), errorsList), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * @param exception PersistenceException
     * @return BaseResponse
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<GenericResponse> handle(PersistenceException exception) {
        log.error("Error message :{}, {}", exception.getMessage(), exception.getStackTrace());
        if (exception.getCause() instanceof ConstraintViolationException) {
            return this.createGlobalException(exception.getCause().getCause().getMessage(),
                    ErrorCodeEnums.INTERNAL_SERVER_ERROR.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return handleException(exception);
        }
    }

    /**
     * @param exception DataIntegrityViolationException
     * @return BaseResponse
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<GenericResponse> handle(DataIntegrityViolationException exception) {
        log.error("DataIntegrityViolationException message ----> {}, {}", exception.getMessage(), exception.getStackTrace());
        return this.createGlobalException(exception.getCause().getCause().getMessage(),
                ErrorCodeEnums.DATA_INTEGRITY_VIOLATION_EXCEPTION.getErrorCode(), HttpStatus.CONFLICT);
    }

    /**
     * @param exception RuntimeException
     * @return BaseResponse
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GenericResponse> handle(RuntimeException exception) {
        log.debug(exception.toString(), exception);
        log.error("RuntimeException Error message --> {}, {}", exception.getMessage(), exception.getStackTrace());
        return this.createGlobalException(
                exception.getMessage(), ErrorCodeEnums.INTERNAL_SERVER_ERROR.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 - Internal server error
     *
     * @param exception Exception
     * @return BaseResponse
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse> handleException(Exception exception) {
        log.debug(exception.toString(), exception);
        log.error("Exception message -->> {}, {}", exception.getMessage(), exception.getStackTrace());
        return this.createGlobalException(
                exception.getMessage(), ErrorCodeEnums.INTERNAL_SERVER_ERROR.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<GenericResponse> createGlobalException(String errorMessage, String errorCode, HttpStatus httpStatus) {
        return new ResponseEntity<>(ErrorResponse.builder().message(errorMessage).errorCode(errorCode).build(), httpStatus);
    }

    private ResponseEntity<GenericResponse> unauthorizedResponse() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.builder().message(ErrorCodeEnums.ACCESS_DENIED.getMessage())
                .errorCode(ErrorCodeEnums.ACCESS_DENIED.getErrorCode()).build());
    }
}
