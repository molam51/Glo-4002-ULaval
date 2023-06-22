package ca.ulaval.glo4002.cafe.domain.coffeeShop.exceptions;

public class InsufficientSeatsException extends RuntimeException {

    private static final String ERROR_MESSAGE = "There are currently no available seats. Please come back later.";

    public InsufficientSeatsException() {
        super(ERROR_MESSAGE);
    }
}
