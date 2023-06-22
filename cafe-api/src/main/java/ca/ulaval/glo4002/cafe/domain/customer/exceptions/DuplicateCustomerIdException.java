package ca.ulaval.glo4002.cafe.domain.customer.exceptions;

public class DuplicateCustomerIdException extends RuntimeException {

    private static final String ERROR_MESSAGE = "The customer cannot visit the café multiple times in the same day.";

    public DuplicateCustomerIdException() {
        super(ERROR_MESSAGE);
    }
}
