package ca.ulaval.glo4002.cafe.domain.coffeeShop;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CubeTest {

    private static final Random RANDOM = new Random();
    private static final String A_CUBE_NAME = "Foo";
    private static final String A_GROUP_NAME = "Who";
    private static final String ANOTHER_GROUP_NAME = "What";
    private static final CustomerID A_CUSTOMER_ID = new CustomerID("1234");

    @Test
    public void givenSeatsInDifferentStates_whenFindAvailableSeats_thenReturnOnlyAvailableSeats() {
        Seat availableSeat = givenAvailableSeat();
        List<Seat> seats = List.of(availableSeat, givenReservedSeat(), givenOccupiedSeat());
        Cube cube = new Cube(A_CUBE_NAME, seats);

        List<Seat> availableSeats = cube.findAvailableSeats();

        List<Seat> expectedSeats = List.of(availableSeat);
        assertIterableEquals(expectedSeats, availableSeats);
    }

    @Test
    public void givenSeatsOwnedByDifferentGroups_whenFindSeatsByGroupName_thenReturnOnlySeatsOwnedByGroup() {
        Seat seatOwnedByGroup = givenReservedSeat(A_GROUP_NAME);
        Seat seatOwnedByAnotherGroup = givenReservedSeat(ANOTHER_GROUP_NAME);
        List<Seat> seats = List.of(seatOwnedByGroup, seatOwnedByAnotherGroup);
        Cube cube = new Cube(A_CUBE_NAME, seats);

        List<Seat> seatsOwnedByGroup = cube.findSeatsByGroupName(A_GROUP_NAME);

        List<Seat> expectedSeats = List.of(seatOwnedByGroup);
        assertIterableEquals(expectedSeats, seatsOwnedByGroup);
    }

    @Test
    public void givenSeatsInDifferentStates_whenFindReservedSeatsByGroupName_thenReturnOnlyReservedSeats() {
        Seat occupiedSeatOwnedByGroup = givenOccupiedSeat(A_GROUP_NAME);
        Seat reservedSeatOwnedByGroup = givenReservedSeat(A_GROUP_NAME);
        List<Seat> seats = List.of(occupiedSeatOwnedByGroup, reservedSeatOwnedByGroup);
        Cube cube = new Cube(A_CUBE_NAME, seats);

        List<Seat> reservedSeats = cube.findReservedSeatsByGroupName(A_GROUP_NAME);

        List<Seat> expectedSeats = List.of(reservedSeatOwnedByGroup);
        assertIterableEquals(expectedSeats, reservedSeats);
    }

    @Test
    public void givenReservedSeatsOwnedByDifferentGroups_whenFindReservedSeatsByGroupName_thenReturnOnlyReservedSeatsOwnedByGroup() {
        Seat reservedSeatOwnedByGroup = givenReservedSeat(A_GROUP_NAME);
        Seat reservedSeatOwnedByAnotherGroup = givenReservedSeat(ANOTHER_GROUP_NAME);
        List<Seat> seats = List.of(reservedSeatOwnedByGroup, reservedSeatOwnedByAnotherGroup);
        Cube cube = new Cube(A_CUBE_NAME, seats);

        List<Seat> reservedSeats = cube.findReservedSeatsByGroupName(A_GROUP_NAME);

        List<Seat> expectedSeats = List.of(reservedSeatOwnedByGroup);
        assertIterableEquals(expectedSeats, reservedSeats);
    }

    @Test
    public void givenCubeWithAvailableSeats_whenIsFullyAvailable_thenReturnTrue() {
        List<Seat> seats = List.of(givenAvailableSeat(), givenAvailableSeat());
        Cube cube = new Cube(A_CUBE_NAME, seats);

        boolean isFullyAvailable = cube.isFullyAvailable();

        assertTrue(isFullyAvailable);
    }

    @Test
    public void givenCubeWithSeatsInDifferentStates_whenIsFullyAvailable_thenReturnFalse() {
        List<Seat> seats = List.of(givenAvailableSeat(), givenOccupiedSeat());
        Cube cube = new Cube(A_CUBE_NAME, seats);

        boolean isFullyAvailable = cube.isFullyAvailable();

        assertFalse(isFullyAvailable);
    }

    @Test
    public void givenCubeWithDifferentlyNumberedSeats_whenClearSeat_thenCorrectSeatIsCleared() {
        Seat seatToClear = givenOccupiedSeat();
        Seat seatNotToClear = givenOccupiedSeat();
        List<Seat> seats = List.of(seatToClear, seatNotToClear);
        Cube cube = new Cube(A_CUBE_NAME, seats);

        cube.clearSeat(seatToClear.getNumber());

        assertEquals(SeatState.AVAILABLE, seatToClear.getState());
        assertEquals(SeatState.OCCUPIED, seatNotToClear.getState());
    }

    private Seat givenAvailableSeat() {
        return new Seat(RANDOM.nextInt());
    }

    private Seat givenReservedSeat() {
        return givenReservedSeat(A_GROUP_NAME);
    }

    private Seat givenReservedSeat(String groupName) {
        Seat reservedSeat = new Seat(RANDOM.nextInt());
        reservedSeat.reserve(groupName);

        return reservedSeat;
    }

    private Seat givenOccupiedSeat() {
        return givenOccupiedSeat(A_GROUP_NAME);
    }

    private Seat givenOccupiedSeat(String groupName) {
        Seat occupiedSeat = new Seat(RANDOM.nextInt());
        occupiedSeat.reserve(groupName);
        occupiedSeat.occupy(A_CUSTOMER_ID, groupName);

        return occupiedSeat;
    }
}
