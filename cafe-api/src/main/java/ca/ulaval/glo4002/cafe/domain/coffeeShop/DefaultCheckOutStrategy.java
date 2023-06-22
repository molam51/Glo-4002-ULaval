package ca.ulaval.glo4002.cafe.domain.coffeeShop;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;

import java.util.List;

public class DefaultCheckOutStrategy implements CheckOutStrategy {

    @Override
    public void checkOut(Customer customer, List<Cube> cubes) {
        for (Cube cube : cubes) {
            cube.clearSeat(customer.getSeatNumber());
        }

        if (customer.hasGroup()) {
            List<Seat> seatsOwnedByGroup = findSeatsByGroupName(customer.getGroupName(), cubes);

            if (isLastCustomerOfGroup(seatsOwnedByGroup)) {
                seatsOwnedByGroup.forEach(Seat::clear);
            }
        }
    }

    private List<Seat> findSeatsByGroupName(String groupName, List<Cube> cubes) {
        return cubes.stream()
                .flatMap(cube -> cube.findSeatsByGroupName(groupName).stream().sequential())
                .toList();
    }

    private boolean isLastCustomerOfGroup(List<Seat> seatsOwnedByGroup) {
        return seatsOwnedByGroup.stream()
                .noneMatch(this::isSeatOccupied);
    }

    private boolean isSeatOccupied(Seat seat) {
        return SeatState.OCCUPIED == seat.getState();
    }
}
