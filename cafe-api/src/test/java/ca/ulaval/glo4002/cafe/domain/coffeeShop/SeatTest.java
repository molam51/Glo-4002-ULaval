package ca.ulaval.glo4002.cafe.domain.coffeeShop;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.exceptions.SeatUnavailableException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeatTest {

    private static final int A_SEAT_NUMBER = 7;
    private static final CustomerID A_CUSTOMER_ID = new CustomerID("1234");
    private static final String A_GROUP_NAME = "Foo";
    private static final String ANOTHER_GROUP_NAME = "Bar";
    private static final String NO_GROUP = null;

    @Test
    public void whenGetState_thenReturnAvailableState() {
        Seat seat = new Seat(A_SEAT_NUMBER);

        SeatState seatState = seat.getState();

        assertEquals(SeatState.AVAILABLE, seatState);
    }

    @Test
    public void givenOccupiedSeat_whenGetState_thenReturnOccupiedState() {
        Seat seat = new Seat(A_SEAT_NUMBER);
        seat.occupy(A_CUSTOMER_ID, NO_GROUP);

        SeatState seatState = seat.getState();

        assertEquals(SeatState.OCCUPIED, seatState);
    }

    @Test
    public void givenReservedSeat_whenGetState_thenReturnReservedState() {
        Seat seat = new Seat(A_SEAT_NUMBER);
        seat.reserve(A_GROUP_NAME);

        SeatState seatState = seat.getState();

        assertEquals(SeatState.RESERVED, seatState);
    }

    @Test
    public void givenOccupiedSeat_whenOccupy_thenThrowSeatUnavailableException() {
        Seat seat = new Seat(A_SEAT_NUMBER);
        seat.occupy(A_CUSTOMER_ID, NO_GROUP);

        assertThrows(SeatUnavailableException.class, () -> {
            seat.occupy(A_CUSTOMER_ID, NO_GROUP);
        });
    }

    @Test
    public void givenReservedSeatAndWrongGroupName_whenOccupy_thenThrowSeatUnavailableException() {
        Seat seat = new Seat(A_SEAT_NUMBER);
        seat.reserve(A_GROUP_NAME);

        assertThrows(SeatUnavailableException.class, () -> {
            seat.occupy(A_CUSTOMER_ID, ANOTHER_GROUP_NAME);
        });
    }

    @Test
    public void givenOccupiedSeat_whenReserve_thenThrowSeatUnavailableException() {
        Seat seat = new Seat(A_SEAT_NUMBER);
        seat.occupy(A_CUSTOMER_ID, NO_GROUP);

        assertThrows(SeatUnavailableException.class, () -> {
            seat.reserve(A_GROUP_NAME);
        });
    }

    @Test
    public void givenReservedSeat_whenReserve_thenThrowSeatUnavailableException() {
        Seat seat = new Seat(A_SEAT_NUMBER);
        seat.reserve(A_GROUP_NAME);

        assertThrows(SeatUnavailableException.class, () -> {
            seat.reserve(A_GROUP_NAME);
        });
    }

    @Test
    public void givenSeat_whenClear_thenSeatIsCleared() {
        Seat seat = new Seat(A_SEAT_NUMBER);
        seat.reserve(A_GROUP_NAME);
        seat.occupy(A_CUSTOMER_ID, A_GROUP_NAME);

        seat.clear();

        assertNull(seat.getGroupName());
        assertNull(seat.getCustomerId());
    }
}
