package ca.ulaval.glo4002.cafe.domain.coffeeShop;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import ca.ulaval.glo4002.cafe.domain.tip.TipRate;

import java.util.List;

public class CoffeeShop {

    private final String name;
    private final List<Cube> cubes;
    private final ReservationStrategy reservationStrategy;
    private final CheckInStrategy checkInStrategy;
    private final CheckOutStrategy checkOutStrategy;
    private final TaxRate taxRate;
    private final TipRate tipRate;

    public CoffeeShop(
            String name,
            List<Cube> cubes,
            ReservationStrategy reservationStrategy,
            CheckInStrategy checkInStrategy,
            CheckOutStrategy checkOutStrategy,
            TaxRate taxRate,
            TipRate tipRate) {
        this.name = name;
        this.cubes = cubes;
        this.reservationStrategy = reservationStrategy;
        this.checkInStrategy = checkInStrategy;
        this.checkOutStrategy = checkOutStrategy;
        this.taxRate = taxRate;
        this.tipRate = tipRate;
    }

    public String getName() {
        return name;
    }

    public List<Cube> getCubes() {
        return cubes;
    }

    public ReservationStrategy getReservationStrategy() {
        return reservationStrategy;
    }

    public TaxRate getTaxRate() {
        return taxRate;
    }

    public TipRate getTipRate() {
        return tipRate;
    }

    public List<Integer> reserveSeats(Reservation reservation) {
        return reservationStrategy.reserveSeats(reservation, cubes);
    }

    public int assignCustomerToSeat(Customer customer) {
        return checkInStrategy.checkIn(customer, cubes);
    }

    public void checkOut(Customer customer) {
        checkOutStrategy.checkOut(customer, cubes);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CoffeeShop otherCoffeeShop)) {
            return false;
        }

        return name.equals(otherCoffeeShop.getName());
    }
}
