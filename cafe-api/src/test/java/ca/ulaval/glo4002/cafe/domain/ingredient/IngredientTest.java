package ca.ulaval.glo4002.cafe.domain.ingredient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientTest {

    private static final IngredientName AN_INGREDIENT_NAME = new IngredientName("Potato");
    private static final Quantity A_QUANTITY = new Quantity(5);

    private Ingredient ingredient;

    @BeforeEach
    public void setupIngredient() {
        ingredient = new Ingredient(AN_INGREDIENT_NAME, A_QUANTITY);
    }

    @Test
    public void givenQuantityToAdd_whenAddQuantity_thenQuantityIsAdded() {
        Quantity quantityToAdd = new Quantity(3);

        ingredient.addQuantity(quantityToAdd);

        Quantity expectedQuantity = new Quantity(8);
        assertEquals(expectedQuantity, ingredient.getQuantity());
    }

    @Test
    public void givenQuantityToRemove_whenRemoveQuantity_thenQuantityIsRemoved() {
        Quantity quantityToRemove = new Quantity(3);

        ingredient.removeQuantity(quantityToRemove);

        Quantity expectedQuantity = new Quantity(2);
        assertEquals(expectedQuantity, ingredient.getQuantity());
    }
}
