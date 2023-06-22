package ca.ulaval.glo4002.cafe.domain.coffeeShop;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.exceptions.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.NoGroupSeatsException;

import java.util.List;

public class DefaultCheckInStrategy implements CheckInStrategy {

    @Override
    public int checkIn(Customer customer, List<Cube> cubes) {
        Seat firstPossibleSeat = findFirstPossibleSeat(customer, cubes);

        firstPossibleSeat.occupy(customer.getId(), customer.getGroupName());

        return firstPossibleSeat.getNumber();
    }

    private Seat findFirstPossibleSeat(Customer customer, List<Cube> cubes) {
        if (customer.hasGroup()) {
            return findReservedSeatsByGroupName(customer.getGroupName(), cubes).stream()
                    .findFirst()
                    .orElseThrow(NoGroupSeatsException::new);
        }

        return findAvailableSeats(cubes).stream()
                .findFirst()
                .orElseThrow(InsufficientSeatsException::new);
    }

    private List<Seat> findAvailableSeats(List<Cube> cubes) {
        return cubes.stream()
                .flatMap(cube -> cube.findAvailableSeats().stream().sequential())
                .toList();
    }

    private List<Seat> findReservedSeatsByGroupName(String groupName, List<Cube> cubes) {
        return cubes.stream()
                .flatMap(cube -> cube.findReservedSeatsByGroupName(groupName).stream().sequential())
                .toList();
    }
}
