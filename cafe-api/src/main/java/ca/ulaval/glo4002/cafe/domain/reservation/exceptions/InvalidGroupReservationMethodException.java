package ca.ulaval.glo4002.cafe.domain.reservation.exceptions;

public class InvalidGroupReservationMethodException extends RuntimeException {

    private static final String ERROR_MESSAGE = "The group reservation method is not supported.";

    public InvalidGroupReservationMethodException() {
        super(ERROR_MESSAGE);
    }
}
