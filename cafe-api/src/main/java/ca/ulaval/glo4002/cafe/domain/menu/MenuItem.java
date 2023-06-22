package ca.ulaval.glo4002.cafe.domain.menu;

import java.util.Objects;

public class MenuItem {

    private final MenuItemName name;
    private final Price price;
    private final Recipe recipe;

    public MenuItem(MenuItemName name, Price price, Recipe recipe) {
        this.name = name;
        this.price = price;
        this.recipe = recipe;
    }

    public MenuItemName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof MenuItem otherMenuItem)) {
            return false;
        }

        return Objects.equals(name, otherMenuItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
