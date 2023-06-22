package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.cafe.domain.ingredient.IngredientName;
import ca.ulaval.glo4002.cafe.domain.ingredient.Quantity;
import ca.ulaval.glo4002.cafe.domain.menu.exceptions.InvalidMenuItemNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecipeFactoryTest {

    private static final IngredientName ESPRESSO = new IngredientName("Espresso");
    private static final Quantity A_QUANTITY = new Quantity(60);

    private RecipeFactory recipeFactory;

    @BeforeEach
    public void setup() {
        recipeFactory = new RecipeFactory();
    }

    @Test
    public void givenMenuItemName_whenCreateRecipe_thenRecipeIsCreated() {
        MenuItemName menuItemName = new MenuItemName("Espresso");

        Recipe recipe = recipeFactory.create(menuItemName);

        List<Ingredient> expectedIngredients = List.of(new Ingredient(ESPRESSO, A_QUANTITY));
        assertEquals(expectedIngredients, recipe.getIngredients());
    }

    @Test
    public void givenInvalidMenuItemName_whenCreateRecipe_thenInvalidMenuItemNameExceptionIsThrown() {
        MenuItemName invalidMenuItemName = new MenuItemName("KentuckyFriedChicken");

        assertThrows(InvalidMenuItemNameException.class, () -> {
            recipeFactory.create(invalidMenuItemName);
        });
    }
}
