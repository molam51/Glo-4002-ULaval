package ca.ulaval.glo4002.cafe.domain.inventory.exceptions;

public class InsufficientIngredientsException extends RuntimeException {

    private static final String ERROR_MESSAGE = "There are currently not enough ingredients. Please come back later.";

    public InsufficientIngredientsException() {
        super(ERROR_MESSAGE);
    }
}
