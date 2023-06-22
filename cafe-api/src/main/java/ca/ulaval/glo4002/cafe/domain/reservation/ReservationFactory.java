package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.reservation.exceptions.InvalidGroupSizeException;

public class ReservationFactory {

    private static final int MINIMUM_GROUP_SIZE = 2;

    public Reservation create(String name, int size) {
        if (size < MINIMUM_GROUP_SIZE) {
            throw new InvalidGroupSizeException();
        }

        return new Reservation(name, size);
    }
}
