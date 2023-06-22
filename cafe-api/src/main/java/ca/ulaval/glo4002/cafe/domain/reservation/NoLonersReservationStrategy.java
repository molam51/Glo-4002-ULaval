package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.Cube;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.Seat;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.SeatState;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.exceptions.InsufficientSeatsException;

import java.util.ArrayList;
import java.util.List;

public class NoLonersReservationStrategy implements ReservationStrategy {

    private static final int INSEPARABLE_REMAINING_CUSTOMER_AMOUNT = 3;

    @Override
    public List<Integer> reserveSeats(Reservation reservation, List<Cube> cubes) {
        List<Seat> availableSeats = new ArrayList<>();
        int customersLeftToPlace = reservation.getGroupSize();

        for (Cube cube : findCubesWithMoreThanOneAvailableSeat(cubes)) {
            List<Seat> cubeAvailableSeats = cube.findAvailableSeats();

            if (customersLeftToPlace <= INSEPARABLE_REMAINING_CUSTOMER_AMOUNT
                    && customersLeftToPlace > cubeAvailableSeats.size()) {
                continue;
            } else if (customersLeftToPlace <= INSEPARABLE_REMAINING_CUSTOMER_AMOUNT) {
                addNFirstSeatsFromSourceIntoTarget(availableSeats, cubeAvailableSeats, customersLeftToPlace);
            } else if (customersLeftToPlace - cubeAvailableSeats.size() == 1) {
                addNFirstSeatsFromSourceIntoTarget(availableSeats, cubeAvailableSeats, customersLeftToPlace - 2);
            } else {
                addNFirstSeatsFromSourceIntoTarget(availableSeats, cubeAvailableSeats, cubeAvailableSeats.size());
            }

            customersLeftToPlace = reservation.getGroupSize() - availableSeats.size();

            if (customersLeftToPlace == 0) {
                for (Seat seat : availableSeats) {
                    seat.reserve(reservation.getGroupName());
                }

                return availableSeats.stream()
                        .map(Seat::getNumber)
                        .toList();
            }
        }

        throw new InsufficientSeatsException();
    }

    private void addNFirstSeatsFromSourceIntoTarget(List<Seat> targetList, List<Seat> sourceList, int n) {
        List<Seat> nFirstSeatsFromSource = sourceList.stream().limit(n).toList();
        targetList.addAll(nFirstSeatsFromSource);
    }

    private List<Cube> findCubesWithMoreThanOneAvailableSeat(List<Cube> cubes) {
        return cubes.stream()
                .filter(this::doesCubeHasMoreThanOneAvailableSeat)
                .toList();
    }

    private boolean doesCubeHasMoreThanOneAvailableSeat(Cube cube) {
        return cube.getSeats().stream()
                .filter(this::isSeatAvailable)
                .count() > 1;
    }

    private boolean isSeatAvailable(Seat seat) {
        return SeatState.AVAILABLE == seat.getState();
    }
}
