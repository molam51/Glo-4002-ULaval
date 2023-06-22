package ca.ulaval.glo4002.cafe.infrastructure;

import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.exceptions.ReservationAlreadyInRepositoryException;

import java.util.ArrayList;
import java.util.List;

public class InMemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public void add(Reservation reservation) {
        if (exists(reservation.getGroupName())) {
            throw new ReservationAlreadyInRepositoryException(reservation.getGroupName());
        }

        reservations.add(reservation);
    }

    @Override
    public void clear() {
        reservations.clear();
    }

    @Override
    public boolean exists(String groupName) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.getGroupName().equals(groupName));
    }
}
