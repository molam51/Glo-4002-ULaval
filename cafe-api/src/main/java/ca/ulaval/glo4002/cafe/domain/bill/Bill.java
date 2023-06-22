package ca.ulaval.glo4002.cafe.domain.bill;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.order.Order;

import java.util.List;

public class Bill {

    private final CustomerID customerId;
    private final Price subtotal;
    private final Price taxes;
    private final Price tip;
    private final Price total;
    private final List<Order> orders;

    public Bill(CustomerID customerId, Price subtotal, Price taxes, Price tip, Price total, List<Order> orders) {
        this.customerId = customerId;
        this.subtotal = subtotal;
        this.taxes = taxes;
        this.tip = tip;
        this.total = total;
        this.orders = orders;
    }

    public CustomerID getCustomerID() {
        return customerId;
    }

    public Price getSubtotal() {
        return subtotal;
    }

    public Price getTaxes() {
        return taxes;
    }

    public Price getTip() {
        return tip;
    }

    public Price getTotal() {
        return total;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
