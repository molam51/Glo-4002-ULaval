package ca.ulaval.glo4002.cafe.domain.coffeeShop;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.exceptions.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.NoGroupSeatsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultCheckInStrategyTest {

    private static final Random RANDOM = new Random();
    private static final CustomerID A_CUSTOMER_ID = new CustomerID("1234");
    private static final String A_CUSTOMER_NAME = "John Doe";
    private static final String A_CUBE_NAME = "Tinker Bell";
    private static final String A_GROUP_NAME = "The Scions of the Seventh Dawn";

    private CheckInStrategy checkInStrategy;

    @BeforeEach
    public void setup() {
        checkInStrategy = new DefaultCheckInStrategy();
    }

    @Test
    public void givenCustomerWithoutGroupAndAvailableSeats_whenCheckIn_thenCustomerIsCheckedIn() {
        Customer customerWithoutGroup = givenCustomerWithoutGroup();
        Seat firstAvailableSeat = givenAvailableSeat();
        List<Cube> cubes = List.of(givenCubeWithSeats(givenReservedSeat(), firstAvailableSeat));

        checkInStrategy.checkIn(customerWithoutGroup, cubes);

        assertEquals(SeatState.OCCUPIED, firstAvailableSeat.getState());
    }

    @Test
    public void givenCustomerWithoutGroupAndAvailableSeats_whenCheckIn_thenReturnCheckedInSeatNumber() {
        Customer customerWithoutGroup = givenCustomerWithoutGroup();
        Seat firstAvailableSeat = givenAvailableSeat();
        List<Cube> cubes = List.of(givenCubeWithSeats(givenReservedSeat(), firstAvailableSeat));

        int returnedSeatNumber = checkInStrategy.checkIn(customerWithoutGroup, cubes);

        assertEquals(firstAvailableSeat.getNumber(), returnedSeatNumber);
    }

    @Test
    public void givenCustomerWithoutGroupAndNoAvailableSeats_whenCheckIn_thenThrowInsufficientSeatsException() {
        Customer customerWithoutGroup = givenCustomerWithoutGroup();
        List<Cube> cubes = List.of(givenEmptyCube(), givenEmptyCube());

        assertThrows(InsufficientSeatsException.class, () -> checkInStrategy.checkIn(customerWithoutGroup, cubes));
    }

    @Test
    public void givenCustomerWithGroupAndReservedSeatsOwnedByGroup_whenCheckIn_thenCustomerIsCheckedIn() {
        Customer customerWithGroup = givenCustomerWithGroup();
        Seat firstReservedSeat = givenReservedSeat();
        List<Cube> cubes = List.of(givenCubeWithSeats(givenAvailableSeat(), firstReservedSeat));

        checkInStrategy.checkIn(customerWithGroup, cubes);

        assertEquals(SeatState.OCCUPIED, firstReservedSeat.getState());
    }

    @Test
    public void givenCustomerWithGroupAndReservedSeatsOwnedByGroup_whenCheckIn_thenReturnCheckedInSeatNumber() {
        Customer customerWithGroup = givenCustomerWithGroup();
        Seat firstReservedSeat = givenReservedSeat();
        List<Cube> cubes = List.of(givenCubeWithSeats(givenAvailableSeat(), firstReservedSeat));

        int returnedSeatNumber = checkInStrategy.checkIn(customerWithGroup, cubes);

        assertEquals(firstReservedSeat.getNumber(), returnedSeatNumber);
    }

    @Test
    public void givenCustomerWithGroupAndNoReservedSeats_whenCheckIn_thenThrowNoGroupSeatsException() {
        Customer customerWithGroup = givenCustomerWithGroup();
        List<Cube> cubes = List.of(givenEmptyCube(), givenEmptyCube());

        assertThrows(NoGroupSeatsException.class, () -> checkInStrategy.checkIn(customerWithGroup, cubes));
    }

    private Cube givenEmptyCube() {
        return new Cube(A_CUBE_NAME, Collections.emptyList());
    }

    private Cube givenCubeWithSeats(Seat... reservedSeats) {
        return new Cube(A_CUBE_NAME, List.of(reservedSeats));
    }

    private Seat givenAvailableSeat() {
        return new Seat(RANDOM.nextInt());
    }

    private Seat givenReservedSeat() {
        Seat reservedSeat = new Seat(RANDOM.nextInt());
        reservedSeat.reserve(A_GROUP_NAME);

        return reservedSeat;
    }

    private Customer givenCustomerWithGroup() {
        return new Customer(A_CUSTOMER_ID, A_CUSTOMER_NAME, A_GROUP_NAME);
    }

    private Customer givenCustomerWithoutGroup() {
        return new Customer(A_CUSTOMER_ID, A_CUSTOMER_NAME, null);
    }
}
