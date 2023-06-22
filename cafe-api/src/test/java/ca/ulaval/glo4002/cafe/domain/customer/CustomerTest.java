package ca.ulaval.glo4002.cafe.domain.customer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private static final CustomerID A_CUSTOMER_ID = new CustomerID("1234");
    private static final String A_CUSTOMER_NAME = "Francis";
    private static final String A_GROUP_NAME = "P'tit Dej.";
    private static final int A_SEAT_NUMBER = 13;

    @Test
    public void givenCustomerWithGroup_whenHasGroup_thenReturnTrue() {
        Customer customer = new Customer(A_CUSTOMER_ID, A_CUSTOMER_NAME, A_GROUP_NAME);

        boolean hasGroup = customer.hasGroup();

        assertTrue(hasGroup);
    }

    @Test
    public void givenCustomerWithNoGroup_whenHasGroup_thenReturnFalse() {
        Customer customer = new Customer(A_CUSTOMER_ID, A_CUSTOMER_NAME, null);

        boolean hasGroup = customer.hasGroup();

        assertFalse(hasGroup);
    }

    @Test
    public void givenCustomer_whenAssignSeat_thenSeatIsAssigned() {
        Customer customer = new Customer(A_CUSTOMER_ID, A_CUSTOMER_NAME, A_GROUP_NAME);

        customer.assignSeat(A_SEAT_NUMBER);

        assertEquals(A_SEAT_NUMBER, customer.getSeatNumber());
    }

    @Test
    public void givenCustomerWithSeat_whenRemoveSeat_thenSeatIsSetToNull() {
        Customer customer = new Customer(A_CUSTOMER_ID, A_CUSTOMER_NAME, A_GROUP_NAME);
        customer.assignSeat(A_SEAT_NUMBER);

        customer.removeSeat();

        assertNull(customer.getSeatNumber());
    }
}
