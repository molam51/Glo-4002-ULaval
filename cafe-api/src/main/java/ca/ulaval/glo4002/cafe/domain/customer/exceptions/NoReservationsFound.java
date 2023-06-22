package ca.ulaval.glo4002.cafe.domain.customer.exceptions;

public class NoReservationsFound extends RuntimeException {

    private static final String ERROR_MESSAGE = "No reservations were made today for that group.";

    public NoReservationsFound() {
        super(ERROR_MESSAGE);
    }
}
