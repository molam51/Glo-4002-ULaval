package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.cafe.domain.ingredient.IngredientName;
import ca.ulaval.glo4002.cafe.domain.ingredient.Quantity;
import ca.ulaval.glo4002.cafe.domain.menu.exceptions.InvalidMenuItemNameException;

import java.util.List;

public class RecipeFactory {

    private static final IngredientName ESPRESSO = new IngredientName("Espresso");
    private static final IngredientName WATER = new IngredientName("Water");
    private static final IngredientName CHOCOLATE = new IngredientName("Chocolate");
    private static final IngredientName MILK = new IngredientName("Milk");

    public Recipe create(MenuItemName menuItemName) throws InvalidMenuItemNameException {
        return switch (menuItemName.toString()) {
            case "Americano" -> createAmericanoRecipe();
            case "Dark Roast" -> createDarkRoastRecipe();
            case "Cappuccino" -> createCappuccinoRecipe();
            case "Espresso" -> createEspressoRecipe();
            case "Flat White" -> createFlatWhiteRecipe();
            case "Latte" -> createFlatWhiteRecipe();
            case "Macchiato" -> createMacchiatoRecipe();
            case "Mocha" -> createMochaRecipe();
            default -> throw new InvalidMenuItemNameException(menuItemName);
        };
    }

    private Recipe createAmericanoRecipe() {
        return new Recipe(List.of(
                new Ingredient(ESPRESSO, new Quantity(50)),
                new Ingredient(WATER, new Quantity(50))
        ));
    }

    private Recipe createDarkRoastRecipe() {
        return new Recipe(List.of(
                new Ingredient(ESPRESSO, new Quantity(40)),
                new Ingredient(WATER, new Quantity(40)),
                new Ingredient(CHOCOLATE, new Quantity(10)),
                new Ingredient(MILK, new Quantity(10))
        ));
    }

    private Recipe createCappuccinoRecipe() {
        return new Recipe(List.of(
                new Ingredient(ESPRESSO, new Quantity(50)),
                new Ingredient(WATER, new Quantity(40)),
                new Ingredient(MILK, new Quantity(10))
        ));
    }

    private Recipe createEspressoRecipe() {
        return new Recipe(List.of(
                new Ingredient(ESPRESSO, new Quantity(60))
        ));
    }

    private Recipe createFlatWhiteRecipe() {
        return new Recipe(List.of(
                new Ingredient(ESPRESSO, new Quantity(50)),
                new Ingredient(MILK, new Quantity(50))
        ));
    }

    private Recipe createMacchiatoRecipe() {
        return new Recipe(List.of(
                new Ingredient(ESPRESSO, new Quantity(80)),
                new Ingredient(MILK, new Quantity(20))
        ));
    }

    private Recipe createMochaRecipe() {
        return new Recipe(List.of(
                new Ingredient(ESPRESSO, new Quantity(50)),
                new Ingredient(CHOCOLATE, new Quantity(10)),
                new Ingredient(MILK, new Quantity(40))
        ));
    }
}
