package ca.ulaval.glo4002.cafe.application;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.BillRepository;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BillApplicationServiceTest {

    private static final CustomerID A_CUSTOMER_ID = new CustomerID("1234");

    private Bill bill;
    private BillRepository billRepository;
    private CustomerRepository customerRepository;
    private BillApplicationService billApplicationService;

    @BeforeEach
    public void setup() {
        setupBillRepository();
        setupCustomerRepository();
        setupBillApplicationService();
    }

    private void setupBillRepository() {
        bill = mock(Bill.class);
        billRepository = mock(BillRepository.class);
        when(billRepository.findByCustomerId(A_CUSTOMER_ID))
                .thenReturn(bill);
    }

    private void setupCustomerRepository() {
        customerRepository = mock(CustomerRepository.class);
    }

    private void setupBillApplicationService() {
        billApplicationService = new BillApplicationService(billRepository, customerRepository);
    }

    @Test
    public void givenCustomerId_whenGetBillByCustomerID_thenOrdersForCustomerIdAreReturned() {
        when(customerRepository.exists(A_CUSTOMER_ID)).thenReturn(true);

        Bill returnedBill = billApplicationService.getBillByCustomerId(A_CUSTOMER_ID);

        assertEquals(bill, returnedBill);
    }

    @Test
    public void givenNonExistentCustomerId_whenGetBillByCustomerID_thenThrowCustomerNotFoundException() {
        when(customerRepository.exists(A_CUSTOMER_ID)).thenReturn(false);

        assertThrows(CustomerNotFoundException.class, () -> {
            billApplicationService.getBillByCustomerId(A_CUSTOMER_ID);
        });
    }
}
