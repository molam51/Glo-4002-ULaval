package ca.ulaval.glo4002.cafe.domain.order;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemName;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.menu.Recipe;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    private static final MenuItemName AN_ITEM_NAME = new MenuItemName("Saumon");
    private static final Price A_PRICE = new Price(10.0);
    private static final CustomerID A_CUSTOMER_ID = new CustomerID("1234");

    @Test
    public void givenMenuItem_whenGetItemNameAsString_thenReturnsTheItemNameAsString() {
        MenuItem menuItem = givenMenuItem();
        Order order = new Order(A_CUSTOMER_ID, menuItem);

        String returnedItemName = order.getItemNameAsString();

        String expectedItemName = AN_ITEM_NAME.toString();
        assertEquals(expectedItemName, returnedItemName);
    }

    private MenuItem givenMenuItem() {
        Recipe menuItemRecipe = new Recipe(Collections.emptyList());

        return new MenuItem(AN_ITEM_NAME, A_PRICE, menuItemRecipe);
    }
}
