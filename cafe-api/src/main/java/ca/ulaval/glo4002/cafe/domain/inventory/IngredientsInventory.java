package ca.ulaval.glo4002.cafe.domain.inventory;

import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.cafe.domain.menu.Recipe;

import java.util.List;

public interface IngredientsInventory {

    List<Ingredient> findAll();

    void addIngredients(List<Ingredient> ingredients);

    void removeIngredients(List<Recipe> recipes);

    void clear();
}
