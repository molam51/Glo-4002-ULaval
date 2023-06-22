package ca.ulaval.glo4002.cafe.domain.tip.exceptions;

public class InvalidGroupTipRateException extends RuntimeException {

    private static final String ERROR_MESSAGE = "The group tip rate must be set to a value between 0 to 100.";

    public InvalidGroupTipRateException() {
        super(ERROR_MESSAGE);
    }
}
