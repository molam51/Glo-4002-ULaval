package ca.ulaval.glo4002.cafe.application;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShop;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShopRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.exceptions.DuplicateGroupNameException;

import java.util.List;

public class ReservationApplicationService {

    private final ReservationFactory reservationFactory;
    private final ReservationRepository reservationRepository;
    private final CoffeeShopRepository coffeeShopRepository;

    public ReservationApplicationService(ReservationFactory reservationFactory,
                                         ReservationRepository reservationRepository,
                                         CoffeeShopRepository coffeeShopRepository) {
        this.reservationFactory = reservationFactory;
        this.reservationRepository = reservationRepository;
        this.coffeeShopRepository = coffeeShopRepository;
    }

    public void addReservation(String groupName, int groupSize) {
        if (reservationRepository.exists(groupName)) {
            throw new DuplicateGroupNameException();
        }

        Reservation reservation = reservationFactory.create(groupName, groupSize);
        CoffeeShop coffeeShop = coffeeShopRepository.find();
        coffeeShop.reserveSeats(reservation);

        coffeeShopRepository.save(coffeeShop);
        reservationRepository.add(reservation);
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }
}
