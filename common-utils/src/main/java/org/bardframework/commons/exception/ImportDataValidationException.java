package org.bardframework.commons.exception;

import org.springframework.validation.Errors;

/**
 * Created by v.zafari on 1/26/2016.
 */
public class ImportDataValidationException extends Exception {

    private final Errors errors;
    private Integer rowNumber;

    public ImportDataValidationException(Errors errors) {
        this.errors = errors;
    }

    public ImportDataValidationException(int rowNumber, Errors errors) {
        this(errors);
        this.rowNumber = rowNumber;
    }

    public Errors getErrors() {
        return errors;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }
}
