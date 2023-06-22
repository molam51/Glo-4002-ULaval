package ca.ulaval.glo4002.cafe.domain.customer;

public interface CustomerRepository {

    Customer findById(CustomerID id);

    void clear();

    boolean exists(CustomerID customerId);

    void save(Customer customer);
}
