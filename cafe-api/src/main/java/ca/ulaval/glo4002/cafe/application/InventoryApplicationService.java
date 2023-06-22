package ca.ulaval.glo4002.cafe.application;

import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientsInventory;

import java.util.List;

public class InventoryApplicationService {

    private final IngredientsInventory ingredientsInventory;

    public InventoryApplicationService(IngredientsInventory ingredientsInventory) {
        this.ingredientsInventory = ingredientsInventory;
    }

    public List<Ingredient> getInventory() {
        return ingredientsInventory.findAll();
    }

    public void addIngredientsToInventory(List<Ingredient> ingredients) {
        ingredientsInventory.addIngredients(ingredients);
    }
}

