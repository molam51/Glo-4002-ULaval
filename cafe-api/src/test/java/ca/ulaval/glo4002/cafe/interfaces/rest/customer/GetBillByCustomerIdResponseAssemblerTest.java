package ca.ulaval.glo4002.cafe.interfaces.rest.customer;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetBillByCustomerIdResponseAssemblerTest {

    private static final CustomerID A_CUSTOMER_ID = new CustomerID("123");
    private static final Price A_SUBTOTAL = new Price(1.91001);
    private static final double A_ROUNDED_UP_SUBTOTAL = 1.92;
    private static final Price SOME_TAXES = new Price(2.91001);
    private static final double SOME_ROUNDED_UP_TAXES = 2.92;
    private static final Price A_TOTAL = new Price(3.91001);
    private static final double A_ROUNDED_UP_TOTAL = 3.92;
    private static final List<String> ORDER_NAMES = List.of("Foo", "Bar");
    private static final Price A_TIP = new Price(4.91001);
    private static final double A_ROUNDED_UP_TIP = 4.92;

    private Bill bill;
    private List<Order> orders;
    private GetBillByCustomerIdResponseAssembler getBillByCustomerIdResponseAssembler;

    @BeforeEach
    void setup() {
        setupOrders();

        bill = new Bill(A_CUSTOMER_ID, A_SUBTOTAL, SOME_TAXES, A_TIP, A_TOTAL, orders);

        getBillByCustomerIdResponseAssembler = new GetBillByCustomerIdResponseAssembler();
    }

    private void setupOrders() {
        Order anOrder = mock(Order.class);
        Order anotherOrder = mock(Order.class);

        when(anOrder.getItemNameAsString()).thenReturn(ORDER_NAMES.get(0));
        when(anotherOrder.getItemNameAsString()).thenReturn(ORDER_NAMES.get(1));

        orders = List.of(anOrder, anotherOrder);
    }

    @Test
    public void givenBill_whenToResponse_thenResponseIsAssembled() {
        GetBillByCustomerIdResponse response = getBillByCustomerIdResponseAssembler.toResponse(bill);

        assertEquals(ORDER_NAMES, response.orders);
        assertEquals(A_ROUNDED_UP_SUBTOTAL, response.subtotal);
        assertEquals(SOME_ROUNDED_UP_TAXES, response.taxes);
        assertEquals(A_ROUNDED_UP_TIP, response.tip);
        assertEquals(A_ROUNDED_UP_TOTAL, response.total);
    }
}
