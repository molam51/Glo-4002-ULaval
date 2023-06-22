package ca.ulaval.glo4002.cafe.domain.customer.exceptions;

public class NoGroupSeatsException extends RuntimeException {

    private static final String ERROR_MESSAGE = "There are no more seats reserved for that group.";

    public NoGroupSeatsException() {
        super(ERROR_MESSAGE);
    }
}
