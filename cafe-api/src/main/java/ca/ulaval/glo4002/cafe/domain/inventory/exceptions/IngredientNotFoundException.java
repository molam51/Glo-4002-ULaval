package ca.ulaval.glo4002.cafe.domain.inventory.exceptions;

import ca.ulaval.glo4002.cafe.domain.ingredient.IngredientName;

public class IngredientNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Unable to find ingredient with name %s.";

    public IngredientNotFoundException(IngredientName ingredientName) {
        super(String.format(ERROR_MESSAGE, ingredientName.toString()));
    }
}
