package ca.ulaval.glo4002.cafe.infrastructure;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.CustomerNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCustomerRepository implements CustomerRepository {

    private final List<Customer> customers = new ArrayList<>();

    @Override
    public Customer findById(CustomerID id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public void clear() {
        customers.clear();
    }

    @Override
    public boolean exists(CustomerID customerId) {
        return customers.stream()
                .anyMatch(customer -> customer.getId().equals(customerId));
    }

    @Override
    public void save(Customer customer) {
        if (exists(customer.getId())) {
            int index = customers.indexOf(customer);
            customers.set(index, customer);
        } else {
            customers.add(customer);
        }
    }
}
