package ca.ulaval.glo4002.cafe.domain.coffeeShop;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultCheckOutStrategyTest {

    private static final Random RANDOM = new Random();
    private static final CustomerID A_CUSTOMER_ID = new CustomerID("1234");
    private static final String A_CUSTOMER_NAME = "John Doe";
    private static final String A_GROUP_NAME = "The Avengers";
    private static final String A_CUBE_NAME = "Tinker Bell";

    private static DefaultCheckOutStrategy checkOutStrategy;

    @BeforeAll
    public static void setup() {
        checkOutStrategy = new DefaultCheckOutStrategy();
    }

    @Test
    public void givenCustomerWithoutGroup_whenCheckOut_thenCustomerSeatIsAvailable() {
        Seat customerSeat = givenOccupiedSeatOwnedByGroup();
        List<Cube> cubes = List.of(givenCubeWithSeats(customerSeat));
        Customer customerOnSeat = givenCustomerOnSeat(customerSeat);

        checkOutStrategy.checkOut(customerOnSeat, cubes);

        assertEquals(SeatState.AVAILABLE, customerSeat.getState());
    }

    @Test
    public void givenLastCustomerOfGroup_whenCheckOut_thenReservedSeatsOwnedByGroupAreAvailable() {
        Seat customerSeat = givenOccupiedSeatOwnedByGroup();
        Seat reservedSeatOwnedByGroup = givenReservedSeatOwnedByGroup();
        List<Cube> cubes = List.of(givenCubeWithSeats(customerSeat, reservedSeatOwnedByGroup));
        Customer lastCustomerOfGroup = givenCustomerOnSeat(customerSeat);

        checkOutStrategy.checkOut(lastCustomerOfGroup, cubes);

        assertEquals(SeatState.AVAILABLE, reservedSeatOwnedByGroup.getState());
    }

    private Cube givenCubeWithSeats(Seat... seats) {
        return new Cube(A_CUBE_NAME, List.of(seats));
    }

    private Seat givenReservedSeatOwnedByGroup() {
        Seat reservedSeatOwnedByGroup = new Seat(RANDOM.nextInt());
        reservedSeatOwnedByGroup.reserve(A_GROUP_NAME);

        return reservedSeatOwnedByGroup;
    }

    private Seat givenOccupiedSeatOwnedByGroup() {
        Seat occupiedSeatOwnedByGroup = new Seat(RANDOM.nextInt());
        occupiedSeatOwnedByGroup.reserve(A_GROUP_NAME);
        occupiedSeatOwnedByGroup.occupy(A_CUSTOMER_ID, A_GROUP_NAME);

        return occupiedSeatOwnedByGroup;
    }

    private Customer givenCustomerOnSeat(Seat seat) {
        Customer customerOnSeat = new Customer(seat.getCustomerId(), A_CUSTOMER_NAME, seat.getGroupName());
        customerOnSeat.assignSeat(seat.getNumber());

        return customerOnSeat;
    }
}
