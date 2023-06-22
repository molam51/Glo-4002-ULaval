package ca.ulaval.glo4002.cafe.infrastructure;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryOrderRepository implements OrderRepository {

    private final List<Order> orders = new ArrayList<>();

    @Override
    public void addAll(List<Order> orders) {
        this.orders.addAll(orders);
    }

    @Override
    public List<Order> findByCustomerId(CustomerID customerId) {
        return orders.stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .toList();
    }

    @Override
    public void clear() {
        orders.clear();
    }
}
