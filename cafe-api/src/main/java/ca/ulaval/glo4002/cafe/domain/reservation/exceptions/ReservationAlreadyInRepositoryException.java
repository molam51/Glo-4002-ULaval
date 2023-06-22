package ca.ulaval.glo4002.cafe.domain.reservation.exceptions;

public class ReservationAlreadyInRepositoryException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Reservation with group name %s already exists.";

    public ReservationAlreadyInRepositoryException(String groupName) {
        super(String.format(ERROR_MESSAGE, groupName));
    }
}
