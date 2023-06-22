package ca.ulaval.glo4002.cafe.domain.order;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemName;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.menu.Recipe;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderFactoryTest {

    private static final CustomerID A_CUSTOMER_ID = new CustomerID("foo");
    private static final MenuItemName A_MENU_ITEM_NAME = new MenuItemName("bar");
    private static final Price A_PRICE = new Price(10.0f);

    @Test
    public void givenCustomerIdAndMenuItem_whenCreate_thenOrderIsCreated() {
        MenuItem menuItem = givenMenuItem();
        OrderFactory orderFactory = new OrderFactory();

        Order order = orderFactory.create(A_CUSTOMER_ID, menuItem);

        assertEquals(A_CUSTOMER_ID, order.getCustomerId());
        assertEquals(menuItem, order.getMenuItem());
    }

    private MenuItem givenMenuItem() {
        Recipe menuItemRecipe = new Recipe(Collections.emptyList());

        return new MenuItem(A_MENU_ITEM_NAME, A_PRICE, menuItemRecipe);
    }
}
