package ca.ulaval.glo4002.cafe.domain.order;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;

public class Order {

    private final CustomerID customerId;
    private final MenuItem menuItem;

    public Order(CustomerID customerId, MenuItem menuItem) {
        this.customerId = customerId;
        this.menuItem = menuItem;
    }

    public CustomerID getCustomerId() {
        return customerId;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public String getItemNameAsString() {
        return menuItem.getName().toString();
    }
}
