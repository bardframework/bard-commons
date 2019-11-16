package org.bardframework.commons.web;

import org.bardframework.commons.exception.ImportDataValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created by Vahid Zafari on 1/14/17.
 */
public abstract class ExceptionControllerAdvice {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @Autowired
    protected MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handle(Exception ex) {
        LOGGER.error("unhandled exception occur", ex);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handle(UnsupportedOperationException ex) {
        LOGGER.error("not allowed service called", ex);
    }

    /**
     * handle exception occur in @Validated annotation in RequestMapping methods
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public List<String> handle(MethodArgumentNotValidException ex, Locale locale) {
        LOGGER.debug("method argument not valid, [{}]", ex.getMessage());
        return ex.getBindingResult().getAllErrors().stream().map(error -> messageSource.getMessage(error.getCode(), error.getArguments(), locale)).collect(Collectors.toList());
    }

    @ExceptionHandler(ImportDataValidationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String handle(ImportDataValidationException ex, Locale locale) {
        return messageSource.getMessage("invalid_row_data", new Integer[]{ex.getRowNumber()}, locale);
    }
}
