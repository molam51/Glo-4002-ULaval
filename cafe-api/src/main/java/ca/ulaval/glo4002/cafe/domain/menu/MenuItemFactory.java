package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.menu.exceptions.InvalidMenuItemNameException;

public class MenuItemFactory {

    private final RecipeFactory recipeFactory;

    public MenuItemFactory(RecipeFactory recipeFactory) {
        this.recipeFactory = recipeFactory;
    }

    public MenuItem create(MenuItemName menuItemName) {
        return new MenuItem(menuItemName, getMenuItemPrice(menuItemName), recipeFactory.create(menuItemName));
    }

    private Price getMenuItemPrice(MenuItemName menuItemName) {
        return switch (menuItemName.toString()) {
            case "Americano" -> new Price(2.25);
            case "Dark Roast" -> new Price(2.10);
            case "Cappuccino" -> new Price(3.29);
            case "Espresso", "Latte" -> new Price(2.95);
            case "Flat White" -> new Price(3.75);
            case "Macchiato" -> new Price(4.75);
            case "Mocha" -> new Price(4.15);
            default -> throw new InvalidMenuItemNameException(menuItemName);
        };
    }

}
