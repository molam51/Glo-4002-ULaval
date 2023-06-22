package ca.ulaval.glo4002.cafe.domain.coffeeShop;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;

import java.util.List;

public interface CheckInStrategy {

    int checkIn(Customer customer, List<Cube> cubes);
}
