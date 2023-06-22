package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.Cube;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.Seat;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.exceptions.InsufficientSeatsException;

import java.util.List;

public class DefaultReservationStrategy implements ReservationStrategy {

    @Override
    public List<Integer> reserveSeats(Reservation reservation, List<Cube> cubes) {
        List<Seat> availableSeats = findAvailableSeats(cubes);

        if (availableSeats.size() < reservation.getGroupSize()) {
            throw new InsufficientSeatsException();
        }

        List<Seat> seatsToReserve = availableSeats.subList(0, reservation.getGroupSize());

        for (Seat seat : seatsToReserve) {
            seat.reserve(reservation.getGroupName());
        }

        return seatsToReserve.stream()
                .map(Seat::getNumber)
                .toList();
    }

    private List<Seat> findAvailableSeats(List<Cube> cubes) {
        return cubes.stream()
                .flatMap(cube -> cube.findAvailableSeats().stream().sequential())
                .toList();
    }
}
