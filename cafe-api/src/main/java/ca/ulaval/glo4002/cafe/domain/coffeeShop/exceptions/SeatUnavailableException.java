package ca.ulaval.glo4002.cafe.domain.coffeeShop.exceptions;

public class SeatUnavailableException extends RuntimeException {

    private static final String ERROR_MESSAGE = "The seat number %d is unavailable.";

    public SeatUnavailableException(int seatNumber) {
        super(String.format(ERROR_MESSAGE, seatNumber));
    }
}
