package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.reservation.exceptions.InvalidGroupSizeException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationFactoryTest {

    private static final int A_GROUP_SIZE = 4;
    private static final int AN_INVALID_GROUP_SIZE = 1;
    private static final String A_GROUP_NAME = "Abstract Connoisseurs";

    private static ReservationFactory reservationFactory;

    @BeforeAll
    public static void setup() {
        reservationFactory = new ReservationFactory();
    }

    @Test
    public void whenCreate_thenReservationIsCreated() {
        Reservation reservation = reservationFactory.create(A_GROUP_NAME, A_GROUP_SIZE);

        assertEquals(A_GROUP_NAME, reservation.getGroupName());
        assertEquals(A_GROUP_SIZE, reservation.getGroupSize());
    }

    @Test
    public void givenInvalidGroupSize_whenCreate_thenThrowInvalidGroupSizeException() {
        assertThrows(InvalidGroupSizeException.class, () -> {
            reservationFactory.create(A_GROUP_NAME, AN_INVALID_GROUP_SIZE);
        });
    }
}
