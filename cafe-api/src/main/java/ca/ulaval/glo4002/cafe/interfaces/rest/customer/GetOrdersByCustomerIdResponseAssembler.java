package ca.ulaval.glo4002.cafe.interfaces.rest.customer;

import ca.ulaval.glo4002.cafe.domain.order.Order;

import java.util.List;

public class GetOrdersByCustomerIdResponseAssembler {

    public GetOrdersByCustomerIdResponse toResponse(List<Order> orders) {
        List<String> orderItemNames = orders.stream()
                .map(Order::getItemNameAsString)
                .toList();

        return new GetOrdersByCustomerIdResponse(orderItemNames);
    }
}
