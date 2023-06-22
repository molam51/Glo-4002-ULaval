package ca.ulaval.glo4002.cafe.domain.tax.exceptions;

public class InvalidCountryException extends RuntimeException {

    private static final String ERROR_MESSAGE = "The specified country is invalid.";

    public InvalidCountryException() {
        super(ERROR_MESSAGE);
    }
}
