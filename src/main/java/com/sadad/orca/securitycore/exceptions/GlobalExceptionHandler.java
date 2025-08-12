/*
 * Copyright 2017 (c) sadad.co.ir
 */

package com.sadad.orca.securitycore.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String MESSAGE_FORMAT = "%s %s ";
    private static final String VALUE_FOR = "value for";
    private static final String MUST_BE_OF_TYPE = "must be of type";
    private static final String PART_IS_MISSING = "part is missing";
    private static final String PARAMETER_IS_MISSING = "parameter is missing";
    private static final String MESSAGE_FORMAT_WITH_FOR = " %s for %s ";
    private static final String IS_NOT_RESPONSIVE = "is not responsive";
    public static final String PARAMETER = "PARAMETER";



    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorDefinition errorDefinition = ErrorDefinition.CONSTRAINT_VIOLATION_EXCEPTION;
        if (logger.isDebugEnabled()) {
            logger.debug(errorDefinition.getMessage(), ex);
        }
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<ErrorData> errorData = null;
        if (Objects.nonNull(constraintViolations)) {
            errorData = constraintViolations
                .stream()
                .map(fieldError -> new ErrorData(
                    PARAMETER,
                    String.valueOf(fieldError.getInvalidValue()),
                    fieldError.getMessage())
                ).collect(toList());
        }
        return ResponseUtils.createResponseEntity(errorDefinition, errorDefinition.getMessage(), errorData, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({SadadException.class})
    public ResponseEntity<Object> handleSadadException(final SadadException ex) {
        if (logger.isDebugEnabled()) {
            logger.debug(ex.getErrorCode() != null && ex.getErrorCode().getMessage() != null ? ex.getErrorCode().getMessage() : "", ex);
        }
        return ResponseUtils.createResponseEntity(ex.getErrorCode(),
            String.format(MESSAGE_FORMAT, ex.getErrorCode().getMessage(), ex.getErrorMessage() != null ? ex.getErrorMessage() : ""),
            ex.getErrorDomain() != null ? ex.getErrorDomain().getDomain() : ErrorDomainDefinition.GLOBAL.getDomain(),
            ex.getErrorData(),
            HttpStatus.valueOf(ex.getErrorCode().getHttpCode()));
    }


    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(final Exception ex) {
        ErrorDefinition errorDefinition = ErrorDefinition.ACCESS_FORBIDEN_EXCEPTION;
        if (logger.isDebugEnabled()) {
            logger.debug(errorDefinition.getMessage(), ex);
        }
        return ResponseUtils.createResponseEntity(errorDefinition, String.format(MESSAGE_FORMAT, errorDefinition.getMessage(), ex.getMessage()), HttpStatus.valueOf(errorDefinition.getHttpCode()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex) {
        ErrorDefinition errorDefinition = ErrorDefinition.INTERNAL_SERVER_ERROR_EXCEPTION;
        if (logger.isDebugEnabled()) {
            logger.debug(errorDefinition.getMessage(), ex);
        }
        return ResponseUtils.createResponseEntity(errorDefinition, errorDefinition.getMessage(), HttpStatus.valueOf(errorDefinition.getHttpCode()));
    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorDefinition errorDefinition = ErrorDefinition.CONSTRAINT_VIOLATION_EXCEPTION;
        if (logger.isDebugEnabled()) {
            logger.debug(errorDefinition.getMessage(), ex);
        }
        BindingResult bindingResult = ex.getBindingResult();
        List<ErrorData> errorData = null;
        if (Objects.nonNull(ex.getBindingResult())) {
            errorData = bindingResult
                    .getFieldErrors()
                    .stream()
                    .map(fieldError -> new ErrorData(
                            fieldError.getField(),
                            fieldError.getCode(),
                            fieldError.getDefaultMessage())
                    ).collect(toList());
        }
        return ResponseUtils.createResponseEntity(errorDefinition, errorDefinition.getMessage(), errorData, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorDefinition errorDefinition = ErrorDefinition.MESSAGE_NOT_READABLE_EXCEPTION;
        if (logger.isDebugEnabled()) {
            logger.debug(errorDefinition.getMessage(), ex);
        }
        return ResponseUtils.createResponseEntity(errorDefinition, errorDefinition.getMessage(), HttpStatus.valueOf(errorDefinition.getHttpCode()));
    }
}
