package ca.ulaval.glo4002.cafe.interfaces.rest.customer;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetCustomerResponseAssemblerTest {

    private static final String A_CUSTOMER_NAME = "Bob";
    private static final String A_GROUP_NAME = "P'tit Dej.";
    private static final int A_SEAT_NUMBER = 13;

    private Customer customer;
    private GetCustomerResponseAssembler getCustomerResponseAssembler;

    @BeforeEach
    void setup() {
        setupMocks();

        getCustomerResponseAssembler = new GetCustomerResponseAssembler();
    }

    private void setupMocks() {
        customer = mock(Customer.class);
        when(customer.getName()).thenReturn(A_CUSTOMER_NAME);
        when(customer.getGroupName()).thenReturn(A_GROUP_NAME);
        when(customer.getSeatNumber()).thenReturn(A_SEAT_NUMBER);
    }

    @Test
    public void givenCustomer_whenToResponse_thenResponseIsAssembled() {
        GetCustomerResponse customerResponse = getCustomerResponseAssembler.toResponse(customer);

        assertEquals(A_CUSTOMER_NAME, customerResponse.name);
        assertEquals(A_SEAT_NUMBER, customerResponse.seatNumber);
        assertEquals(A_GROUP_NAME, customerResponse.groupName);
    }
}
