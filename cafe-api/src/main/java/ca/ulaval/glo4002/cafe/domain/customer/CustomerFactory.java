package ca.ulaval.glo4002.cafe.domain.customer;

public class CustomerFactory {

    public Customer create(CustomerID id, String name, String groupName) {
        return new Customer(id, name, groupName);
    }
}
