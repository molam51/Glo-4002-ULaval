package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.Cube;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.Seat;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.exceptions.InsufficientSeatsException;

import java.util.ArrayList;
import java.util.List;

public class FullCubesReservationStrategy implements ReservationStrategy {

    @Override
    public List<Integer> reserveSeats(Reservation reservation, List<Cube> cubes) {
        List<Cube> fullyAvailableCubes = findFullyAvailableCubes(cubes);

        List<Seat> seatsToReserve = new ArrayList<>();

        for (Cube cube : fullyAvailableCubes) {
            seatsToReserve.addAll(cube.getSeats());

            if (seatsToReserve.size() >= reservation.getGroupSize()) {
                for (Seat seat : seatsToReserve) {
                    seat.reserve(reservation.getGroupName());
                }

                return seatsToReserve.stream()
                        .map(Seat::getNumber)
                        .toList();
            }
        }

        throw new InsufficientSeatsException();
    }

    private List<Cube> findFullyAvailableCubes(List<Cube> cubes) {
        return cubes.stream()
                .filter(Cube::isFullyAvailable)
                .toList();
    }
}
