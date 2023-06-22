package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.cafe.domain.ingredient.IngredientName;
import ca.ulaval.glo4002.cafe.domain.ingredient.Quantity;
import ca.ulaval.glo4002.cafe.domain.menu.exceptions.InvalidMenuItemNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MenuItemFactoryTest {

    private static final IngredientName ESPRESSO = new IngredientName("Espresso");
    private static final MenuItemName A_MENU_ITEM_NAME = new MenuItemName("Espresso");
    private static final Price A_PRICE = new Price(2.95);
    private static final Recipe A_RECIPE = new Recipe(List.of(
            new Ingredient(ESPRESSO, new Quantity(60))));
    private static final MenuItem A_MENU_ITEM = new MenuItem(A_MENU_ITEM_NAME, A_PRICE, A_RECIPE);

    private MenuItemFactory menuItemFactory;

    @BeforeEach
    public void setup() {
        RecipeFactory recipeFactory = mock(RecipeFactory.class);
        when(recipeFactory.create(A_MENU_ITEM_NAME)).thenReturn(A_RECIPE);

        menuItemFactory = new MenuItemFactory(recipeFactory);
    }

    @Test
    public void givenMenuItemName_whenCreateMenuItem_thenMenuItemIsCreated() {
        MenuItemName menuItemName = new MenuItemName("Espresso");

        MenuItem menuItem = menuItemFactory.create(menuItemName);

        assertEquals(A_MENU_ITEM, menuItem);
    }

    @Test
    public void givenInvalidMenuItemName_whenCreateMenuItem_thenInvalidMenuItemNameExceptionIsThrown() {
        MenuItemName invalidMenuItemName = new MenuItemName("KentuckyFriedChicken");

        assertThrows(InvalidMenuItemNameException.class, () -> {
            menuItemFactory.create(invalidMenuItemName);
        });
    }
}
