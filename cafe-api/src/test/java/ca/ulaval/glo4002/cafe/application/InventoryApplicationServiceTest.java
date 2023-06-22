package ca.ulaval.glo4002.cafe.application;

import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.cafe.domain.ingredient.IngredientName;
import ca.ulaval.glo4002.cafe.domain.ingredient.Quantity;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientsInventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InventoryApplicationServiceTest {

    private static final IngredientName AN_INGREDIENT_NAME = new IngredientName("Potato");
    private static final IngredientName ANOTHER_INGREDIENT_NAME = new IngredientName("Pasta");
    private static final Quantity A_QUANTITY = new Quantity(5);
    private static final Ingredient AN_INGREDIENT = new Ingredient(AN_INGREDIENT_NAME, A_QUANTITY);
    private static final Ingredient ANOTHER_INGREDIENT = new Ingredient(ANOTHER_INGREDIENT_NAME, A_QUANTITY);

    private InventoryApplicationService inventoryApplicationService;
    private IngredientsInventory ingredientsInventory;

    @BeforeEach
    public void setup() {
        ingredientsInventory = mock(IngredientsInventory.class);

        inventoryApplicationService = new InventoryApplicationService(ingredientsInventory);
    }

    @Test
    public void givenIngredients_whenGetIngredients_thenReturnIngredientsFromIngredientInventory() {
        List<Ingredient> expectedIngredients = List.of(AN_INGREDIENT, ANOTHER_INGREDIENT);
        when(ingredientsInventory.findAll()).thenReturn(expectedIngredients);

        List<Ingredient> returnedIngredients = inventoryApplicationService.getInventory();

        assertEquals(expectedIngredients, returnedIngredients);
    }

    @Test
    public void givenIngredients_whenAddIngredients_thenAddIngredientsIsCalledOnInventoryInterface() {
        List<Ingredient> ingredientsToAdd = List.of(AN_INGREDIENT, ANOTHER_INGREDIENT);

        inventoryApplicationService.addIngredientsToInventory(ingredientsToAdd);

        verify(ingredientsInventory).addIngredients(ingredientsToAdd);
    }
}
