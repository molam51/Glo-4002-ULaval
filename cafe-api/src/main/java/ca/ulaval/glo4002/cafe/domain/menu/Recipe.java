package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;

import java.util.List;

public class Recipe {

    private final List<Ingredient> ingredients;

    public Recipe(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
