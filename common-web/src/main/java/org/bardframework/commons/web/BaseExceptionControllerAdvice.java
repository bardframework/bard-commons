package org.bardframework.commons.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Vahid Zafari on 1/14/17.
 */
public interface BaseExceptionControllerAdvice {

    Logger log = LoggerFactory.getLogger(BaseExceptionControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    default void handle(Exception ex) {
        log.error("unhandled exception occur", ex);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    default void handle(UnsupportedOperationException ex) {
        log.error("not allowed service called", ex);
    }

    /**
     * handle exception occur in @Validated annotation in RequestMapping methods
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    default List<String> handle(MethodArgumentNotValidException ex, Locale locale) {
        log.debug("method argument not valid, [{}]", ex.getMessage());
        return ex.getBindingResult().getAllErrors().stream()
                .map(error -> this.getMessageSource().getMessage(Objects.requireNonNull(error.getCode()), error.getArguments(), locale))
                .collect(Collectors.toList());
    }

    /**
     * BindException:   occur in @ModelAttribute annotation in RequestMapping methods
     */
    @ExceptionHandler({
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            BindException.class
    })
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    default List<String> handleInvalidDataError(Exception ex) {
        log.error("exception occurred reading request data ", ex);
        return List.of(ex.getMessage());
    }


    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    default void handle(AuthenticationException ex) {
        log.error("Authentication Error: ", ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    default void handle(AccessDeniedException ex) {
        log.warn("AccessDenied: ", ex);
    }

    MessageSource getMessageSource();
}
