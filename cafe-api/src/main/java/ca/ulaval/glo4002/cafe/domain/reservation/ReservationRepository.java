package ca.ulaval.glo4002.cafe.domain.reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    void add(Reservation reservation);

    void clear();

    boolean exists(String groupName);
}
