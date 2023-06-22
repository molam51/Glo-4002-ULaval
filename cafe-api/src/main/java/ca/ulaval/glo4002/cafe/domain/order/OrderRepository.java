package ca.ulaval.glo4002.cafe.domain.order;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;

import java.util.List;

public interface OrderRepository {

    void addAll(List<Order> orders);

    List<Order> findByCustomerId(CustomerID customerId);

    void clear();
}
