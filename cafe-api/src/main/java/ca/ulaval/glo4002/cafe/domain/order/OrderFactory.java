package ca.ulaval.glo4002.cafe.domain.order;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;

public class OrderFactory {

    public Order create(CustomerID customerId, MenuItem menuItem) {
        return new Order(customerId, menuItem);
    }
}
