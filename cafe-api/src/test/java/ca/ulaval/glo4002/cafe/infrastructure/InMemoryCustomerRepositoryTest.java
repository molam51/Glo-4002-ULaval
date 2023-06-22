package ca.ulaval.glo4002.cafe.infrastructure;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryCustomerRepositoryTest {

    private static final CustomerID A_CUSTOMER_ID = new CustomerID("1234");
    private static final CustomerID ANOTHER_CUSTOMER_ID = new CustomerID("5678");
    private static final String A_CUSTOMER_NAME = "Neil Down";

    private CustomerRepository customerRepository;

    @BeforeEach
    public void setup() {
        customerRepository = new InMemoryCustomerRepository();
    }

    @Test
    public void givenEmptyRepository_whenFindById_thenThrowsCustomerNotFoundException() {
        assertThrows(CustomerNotFoundException.class, () -> {
            customerRepository.findById(A_CUSTOMER_ID);
        });
    }

    @Test
    public void givenCustomer_whenSave_thenCustomerIsFoundInRepository() {
        Customer customer = givenCustomer();

        customerRepository.save(customer);

        Customer foundCustomer = customerRepository.findById(customer.getId());
        assertEquals(customer, foundCustomer);
    }

    @Test
    public void givenMultipleCustomersInRepository_whenFindById_thenReturnTheRightCustomer() {
        Customer customer = givenCustomer();
        Customer anotherCustomer = givenAnotherCustomer();
        customerRepository.save(customer);
        customerRepository.save(anotherCustomer);

        Customer actualCustomer = customerRepository.findById(customer.getId());
        Customer actualAnotherCustomer = customerRepository.findById(anotherCustomer.getId());

        assertEquals(customer, actualCustomer);
        assertEquals(anotherCustomer, actualAnotherCustomer);
    }

    @Test
    public void givenCustomerAlreadyInRepository_whenExists_thenReturnTrue() {
        Customer customer = givenCustomer();
        customerRepository.save(customer);

        boolean exists = customerRepository.exists(A_CUSTOMER_ID);

        assertTrue(exists);
    }

    @Test
    public void givenCustomerNotInRepository_whenExists_thenReturnFalse() {
        Customer anotherCustomer = givenAnotherCustomer();
        customerRepository.save(anotherCustomer);

        boolean exists = customerRepository.exists(A_CUSTOMER_ID);

        assertFalse(exists);
    }

    @Test
    public void givenMultipleCustomersInRepository_whenClear_thenRepositoryIsEmpty() {
        Customer customer = givenCustomer();
        Customer anotherCustomer = givenAnotherCustomer();
        customerRepository.save(customer);
        customerRepository.save(anotherCustomer);

        customerRepository.clear();

        assertThrows(CustomerNotFoundException.class, () -> customerRepository.findById(customer.getId()));
        assertThrows(CustomerNotFoundException.class, () -> customerRepository.findById(anotherCustomer.getId()));
    }

    @Test
    public void givenCustomerInRepo_whenSave_thenCustomerIsUpdated() {
        Customer customer = givenCustomer();
        Customer updatedCustomer = givenCustomer();
        customerRepository.save(customer);

        customerRepository.save(updatedCustomer);

        Customer customerInRepo = customerRepository.findById(A_CUSTOMER_ID);
        assertSame(updatedCustomer, customerInRepo);
    }

    private Customer givenCustomer() {
        return new Customer(A_CUSTOMER_ID, A_CUSTOMER_NAME, null);
    }

    private Customer givenAnotherCustomer() {
        return new Customer(ANOTHER_CUSTOMER_ID, A_CUSTOMER_NAME, null);
    }
}
