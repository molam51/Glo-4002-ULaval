package ca.ulaval.glo4002.cafe.infrastructure;

import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.cafe.domain.ingredient.IngredientName;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientsInventory;
import ca.ulaval.glo4002.cafe.domain.inventory.exceptions.IngredientNotFoundException;
import ca.ulaval.glo4002.cafe.domain.inventory.exceptions.InsufficientIngredientsException;
import ca.ulaval.glo4002.cafe.domain.menu.Recipe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryIngredientsInventory implements IngredientsInventory {

    private final Map<IngredientName, Ingredient> ingredients = new HashMap<>();

    @Override
    public List<Ingredient> findAll() {
        return ingredients.values().stream().toList();
    }

    private Ingredient findByName(IngredientName ingredientName) {
        Ingredient ingredientInInventory = ingredients.get(ingredientName);

        if (ingredientInInventory == null) {
            throw new IngredientNotFoundException(ingredientName);
        }

        return ingredientInInventory;
    }

    @Override
    public void addIngredients(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            addIngredientToInventory(ingredient);
        }
    }

    private void addIngredientToInventory(Ingredient ingredient) {
        Ingredient ingredientInInventory = ingredients.get(ingredient.getIngredientName());

        if (ingredientInInventory == null) {
            ingredients.put(ingredient.getIngredientName(), ingredient);
        } else {
            ingredientInInventory.addQuantity(ingredient.getQuantity());
        }
    }

    @Override
    public void removeIngredients(List<Recipe> recipes) {
        List<Ingredient> totalIngredients = calculateTotalIngredients(recipes);

        if (!hasEnoughIngredients(totalIngredients)) {
            throw new InsufficientIngredientsException();
        }

        removeIngredientsFromInventory(totalIngredients);
    }

    private List<Ingredient> calculateTotalIngredients(List<Recipe> recipes) {
        Map<IngredientName, Ingredient> totalIngredients = new HashMap<>();

        for (Recipe recipe : recipes) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                Ingredient ingredientInTotalIngredients = totalIngredients.get(ingredient.getIngredientName());

                if (ingredientInTotalIngredients == null) {
                    totalIngredients.put(ingredient.getIngredientName(), ingredient.copy());
                } else {
                    ingredientInTotalIngredients.addQuantity(ingredient.getQuantity());
                }
            }
        }

        return totalIngredients.values().stream().toList();
    }

    private boolean hasEnoughIngredients(List<Ingredient> ingredients) {
        return ingredients.stream().allMatch(this::hasEnoughIngredients);
    }

    private boolean hasEnoughIngredients(Ingredient ingredient) {
        Ingredient ingredientInInventory = findByName(ingredient.getIngredientName());

        return ingredientInInventory.getQuantity().greaterOrEqualThan(ingredient.getQuantity());
    }

    private void removeIngredientsFromInventory(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            removeIngredientFromInventory(ingredient);
        }
    }

    private void removeIngredientFromInventory(Ingredient ingredient) {
        Ingredient ingredientInInventory = findByName(ingredient.getIngredientName());

        ingredientInInventory.removeQuantity(ingredient.getQuantity());
    }

    @Override
    public void clear() {
        ingredients.clear();
    }
}
