package ca.ulaval.glo4002.cafe.domain.reservation.exceptions;

public class DuplicateGroupNameException extends RuntimeException {

    private static final String ERROR_MESSAGE = "The specified group already made a reservation today.";

    public DuplicateGroupNameException() {
        super(ERROR_MESSAGE);
    }
}
