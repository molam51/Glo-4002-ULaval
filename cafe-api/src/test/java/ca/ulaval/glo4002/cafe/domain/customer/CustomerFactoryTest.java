package ca.ulaval.glo4002.cafe.domain.customer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerFactoryTest {

    private static final CustomerID A_CUSTOMER_ID = new CustomerID("1234");
    private static final String A_CUSTOMER_NAME = "John Doe";
    private static final String A_GROUP_NAME = "P'tit Dej.";

    @Test
    public void whenCreate_thenCustomerIsCreated() {
        CustomerFactory customerFactory = new CustomerFactory();

        Customer customer = customerFactory.create(A_CUSTOMER_ID, A_CUSTOMER_NAME, A_GROUP_NAME);

        assertEquals(A_CUSTOMER_ID, customer.getId());
        assertEquals(A_CUSTOMER_NAME, customer.getName());
        assertEquals(A_GROUP_NAME, customer.getGroupName());
    }
}
