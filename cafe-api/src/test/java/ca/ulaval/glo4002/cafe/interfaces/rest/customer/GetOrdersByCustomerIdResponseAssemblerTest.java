package ca.ulaval.glo4002.cafe.interfaces.rest.customer;

import ca.ulaval.glo4002.cafe.domain.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetOrdersByCustomerIdResponseAssemblerTest {

    private static final String AN_ITEM_NAME = "Saumon";
    private static final String ANOTHER_ITEM_NAME = "Vaseline";

    private Order anOrder;
    private Order anotherOrder;
    private GetOrdersByCustomerIdResponseAssembler getOrdersByCustomerIdResponseAssembler;

    @BeforeEach
    void setup() {
        setupOrders();

        getOrdersByCustomerIdResponseAssembler = new GetOrdersByCustomerIdResponseAssembler();
    }

    private void setupOrders() {
        anOrder = mock(Order.class);
        anotherOrder = mock(Order.class);

        when(anOrder.getItemNameAsString()).thenReturn(AN_ITEM_NAME);
        when(anotherOrder.getItemNameAsString()).thenReturn(ANOTHER_ITEM_NAME);
    }

    @Test
    public void givenOrders_whenToResponse_thenResponseContainsOrdersMenuItemNames() {
        List<Order> orders = List.of(anOrder, anotherOrder);

        GetOrdersByCustomerIdResponse getOrdersByCustomerIdResponse =
                getOrdersByCustomerIdResponseAssembler.toResponse(orders);

        assertTrue(getOrdersByCustomerIdResponse.orders.contains(AN_ITEM_NAME));
        assertTrue(getOrdersByCustomerIdResponse.orders.contains(ANOTHER_ITEM_NAME));
    }
}
