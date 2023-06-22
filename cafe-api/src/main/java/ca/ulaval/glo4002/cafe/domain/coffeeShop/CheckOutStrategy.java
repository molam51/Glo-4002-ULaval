package ca.ulaval.glo4002.cafe.domain.coffeeShop;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;

import java.util.List;

public interface CheckOutStrategy {

    void checkOut(Customer customer, List<Cube> cubes);
}
