package ca.ulaval.glo4002.cafe.infrastructure;

import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.cafe.domain.ingredient.IngredientName;
import ca.ulaval.glo4002.cafe.domain.ingredient.Quantity;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemName;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.menu.Recipe;
import ca.ulaval.glo4002.cafe.domain.menu.exceptions.DuplicateMenuItemNameException;
import ca.ulaval.glo4002.cafe.domain.menu.exceptions.MenuItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryMenuTest {

    private static final MenuItemName A_MENU_ITEM_NAME = new MenuItemName("Foo");
    private static final MenuItemName ANOTHER_MENU_ITEM_NAME = new MenuItemName("Bar");
    private static final Price A_PRICE = new Price(10.0f);
    private static final IngredientName AN_INGREDIENT_NAME = new IngredientName("Espresso");
    private static final Recipe A_RECIPE = new Recipe(List.of(
            new Ingredient(AN_INGREDIENT_NAME, new Quantity(80))));

    private InMemoryMenu inMemoryMenu;

    @BeforeEach
    public void setup() {
        inMemoryMenu = new InMemoryMenu();
    }

    @Test
    public void givenMenuItem_whenAdd_thenMenuItemIsAdded() {
        MenuItem menuItem = givenMenuItem();

        inMemoryMenu.add(menuItem);

        MenuItem foundMenuItem = inMemoryMenu.findByName(A_MENU_ITEM_NAME);
        assertSame(menuItem, foundMenuItem);
    }

    @Test
    public void givenMenuItemInMenu_whenAdd_thenThrowDuplicateMenuItemNameException() {
        MenuItem menuItem = givenMenuItem();
        inMemoryMenu.add(menuItem);

        assertThrows(DuplicateMenuItemNameException.class, () -> {
            inMemoryMenu.add(menuItem);
        });
    }

    @Test
    public void givenMultipleMenuItemsInMenu_whenFindByName_thenCorrespondingMenuItemIsReturned() {
        MenuItem menuItem = givenMenuItem();
        MenuItem anotherMenuItem = givenAnotherMenuItem();
        inMemoryMenu.add(menuItem);
        inMemoryMenu.add(anotherMenuItem);

        MenuItem returnedMenuItem = inMemoryMenu.findByName(A_MENU_ITEM_NAME);

        assertSame(menuItem, returnedMenuItem);
    }

    @Test
    public void givenMenuItemNotInMenu_whenFindByName_thenThrowMenuItemNotFoundException() {
        assertThrows(MenuItemNotFoundException.class, () -> {
            inMemoryMenu.findByName(A_MENU_ITEM_NAME);
        });
    }

    private MenuItem givenMenuItem() {
        return new MenuItem(A_MENU_ITEM_NAME, A_PRICE, A_RECIPE);
    }

    private MenuItem givenAnotherMenuItem() {
        return new MenuItem(ANOTHER_MENU_ITEM_NAME, A_PRICE, A_RECIPE);
    }
}
