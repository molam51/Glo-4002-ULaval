package ca.ulaval.glo4002.cafe.infrastructure;

import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.cafe.domain.ingredient.IngredientName;
import ca.ulaval.glo4002.cafe.domain.ingredient.Quantity;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientsInventory;
import ca.ulaval.glo4002.cafe.domain.inventory.exceptions.IngredientNotFoundException;
import ca.ulaval.glo4002.cafe.domain.inventory.exceptions.InsufficientIngredientsException;
import ca.ulaval.glo4002.cafe.domain.menu.Recipe;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryIngredientsInventoryTest {

    private static final IngredientName AN_INGREDIENT_NAME = new IngredientName("chocolate");
    private static final Quantity A_QUANTITY = new Quantity(5);
    private static final Quantity A_LOWER_QUANTITY = new Quantity(3);
    private static final Quantity A_HIGHER_QUANTITY = new Quantity(8);

    @Test
    public void givenEmptyIngredientsInventory_whenFindAll_thenReturnNoIngredients() {
        IngredientsInventory ingredientsInventory = givenInventoryWithIngredients();

        List<Ingredient> foundIngredients = ingredientsInventory.findAll();

        List<Ingredient> expectedIngredients = Collections.emptyList();
        assertEquals(expectedIngredients, foundIngredients);
    }

    @Test
    public void givenIngredientsInInventory_whenFindAll_thenReturnAllIngredients() {
        Ingredient ingredientInInventory = givenIngredient();
        IngredientsInventory ingredientsInventory = givenInventoryWithIngredients(ingredientInInventory);

        List<Ingredient> foundIngredients = ingredientsInventory.findAll();

        List<Ingredient> expectedIngredients = List.of(ingredientInInventory);
        assertEquals(expectedIngredients, foundIngredients);
    }

    @Test
    public void givenIngredientsNotInInventory_whenAddIngredients_thenIngredientsAreAddedToInventory() {
        Ingredient ingredientToAdd = givenIngredient();
        IngredientsInventory ingredientsInventory = givenInventoryWithIngredients();

        ingredientsInventory.addIngredients(List.of(ingredientToAdd));

        List<Ingredient> foundIngredients = ingredientsInventory.findAll();
        List<Ingredient> expectedIngredients = List.of(ingredientToAdd);
        assertEquals(expectedIngredients, foundIngredients);
    }

    @Test
    public void givenIngredientsAlreadyInInventory_whenAddIngredients_thenIngredientsAreAddedToInventory() {
        Ingredient ingredientInInventory = givenIngredientWithQuantity(A_QUANTITY);
        Ingredient ingredientToAdd = givenIngredientWithQuantity(A_LOWER_QUANTITY);
        IngredientsInventory ingredientsInventory = givenInventoryWithIngredients(ingredientInInventory);

        ingredientsInventory.addIngredients(List.of(ingredientToAdd));

        Quantity expectedQuantity = A_QUANTITY.add(A_LOWER_QUANTITY);
        assertEquals(expectedQuantity, ingredientInInventory.getQuantity());
    }

    @Test
    public void givenRecipeWithIngredientsNotInInventory_whenRemoveIngredients_thenIngredientNotFoundExceptionIsThrown() {
        Ingredient recipeIngredient = givenIngredient();
        Recipe recipe = givenRecipeWithIngredients(recipeIngredient);
        IngredientsInventory ingredientsInventory = givenInventoryWithIngredients();

        assertThrows(IngredientNotFoundException.class, () -> ingredientsInventory.removeIngredients(List.of(recipe)));
    }

    private Recipe givenRecipeWithIngredients(Ingredient... ingredients) {
        return new Recipe(List.of(ingredients));
    }

    @Test
    public void givenRecipeAndInsufficientIngredientsInInventory_whenRemoveIngredients_thenInsufficientIngredientsExceptionIsThrown() {
        Ingredient ingredientInInventory = givenIngredientWithQuantity(A_QUANTITY);
        Ingredient recipeIngredient = givenIngredientWithQuantity(A_HIGHER_QUANTITY);
        Recipe recipe = givenRecipeWithIngredients(recipeIngredient);
        IngredientsInventory ingredientsInventory = givenInventoryWithIngredients(ingredientInInventory);

        assertThrows(InsufficientIngredientsException.class, () -> ingredientsInventory.removeIngredients(List.of(recipe)));
    }

    @Test
    public void givenRecipeAndSufficientIngredientsInInventory_whenRemoveIngredients_thenIngredientsAreRemoved() {
        Ingredient ingredientInInventory = givenIngredientWithQuantity(A_QUANTITY);
        Ingredient recipeIngredient = givenIngredientWithQuantity(A_LOWER_QUANTITY);
        Recipe recipe = givenRecipeWithIngredients(recipeIngredient);
        IngredientsInventory ingredientsInventory = givenInventoryWithIngredients(ingredientInInventory);

        ingredientsInventory.removeIngredients(List.of(recipe));

        Quantity expectedQuantity = A_QUANTITY.remove(A_LOWER_QUANTITY);
        assertEquals(expectedQuantity, ingredientInInventory.getQuantity());
    }

    @Test
    public void givenIngredientsInInventory_whenClear_thenInventoryIsCleared() {
        Ingredient ingredientInInventory = givenIngredient();
        IngredientsInventory ingredientsInventory = givenInventoryWithIngredients(ingredientInInventory);

        ingredientsInventory.clear();

        assertTrue(ingredientsInventory.findAll().isEmpty());
    }

    private IngredientsInventory givenInventoryWithIngredients(Ingredient... ingredients) {
        IngredientsInventory ingredientsInventory = new InMemoryIngredientsInventory();
        ingredientsInventory.addIngredients(List.of(ingredients));
        return ingredientsInventory;
    }

    private Ingredient givenIngredient() {
        return new Ingredient(AN_INGREDIENT_NAME, A_QUANTITY);
    }

    private Ingredient givenIngredientWithQuantity(Quantity quantity) {
        return new Ingredient(AN_INGREDIENT_NAME, quantity);
    }
}
