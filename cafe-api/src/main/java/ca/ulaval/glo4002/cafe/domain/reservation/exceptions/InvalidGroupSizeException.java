package ca.ulaval.glo4002.cafe.domain.reservation.exceptions;

public class InvalidGroupSizeException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Groups must reserve at least two seats.";

    public InvalidGroupSizeException() {
        super(ERROR_MESSAGE);
    }
}
