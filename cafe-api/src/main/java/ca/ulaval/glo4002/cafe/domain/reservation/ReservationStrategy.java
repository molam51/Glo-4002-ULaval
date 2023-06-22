package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.Cube;

import java.util.List;

public interface ReservationStrategy {

    List<Integer> reserveSeats(Reservation reservation, List<Cube> cubes);
}
