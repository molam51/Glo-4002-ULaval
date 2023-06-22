package ca.ulaval.glo4002.cafe.interfaces.rest.inventory;

import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.cafe.domain.ingredient.IngredientName;
import ca.ulaval.glo4002.cafe.domain.ingredient.Quantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetInventoryResponseAssemblerTest {

    private static final Quantity INGREDIENT_QUANTITY = new Quantity(5);
    private static final IngredientName CHOCOLATE_NAME = new IngredientName("Chocolate");
    private static final IngredientName ESPRESSO_NAME = new IngredientName("Espresso");
    private static final IngredientName MILK_NAME = new IngredientName("Milk");
    private static final IngredientName WATER_NAME = new IngredientName("Water");

    private List<Ingredient> ingredients;

    private GetInventoryResponseAssembler getInventoryResponseAssembler;

    @BeforeEach
    void setup() {
        Ingredient chocolate = new Ingredient(CHOCOLATE_NAME, INGREDIENT_QUANTITY);
        Ingredient espresso = new Ingredient(ESPRESSO_NAME, INGREDIENT_QUANTITY);
        Ingredient milk = new Ingredient(MILK_NAME, INGREDIENT_QUANTITY);
        Ingredient water = new Ingredient(WATER_NAME, INGREDIENT_QUANTITY);

        ingredients = List.of(chocolate, espresso, milk, water);

        getInventoryResponseAssembler = new GetInventoryResponseAssembler();
    }

    @Test
    public void givenIngredients_whenToResponse_thenResponseIsAssembled() {
        GetInventoryResponse response = getInventoryResponseAssembler.toResponse(ingredients);

        assertEquals(INGREDIENT_QUANTITY.toInt(), response.chocolate);
        assertEquals(INGREDIENT_QUANTITY.toInt(), response.espresso);
        assertEquals(INGREDIENT_QUANTITY.toInt(), response.milk);
        assertEquals(INGREDIENT_QUANTITY.toInt(), response.water);
    }
}
